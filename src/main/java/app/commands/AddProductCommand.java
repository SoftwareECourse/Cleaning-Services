package app.commands;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Invoice;
import app.Product;
import app.Worker;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "add-product", description = "Add a new product to the system.")
public class AddProductCommand implements Runnable {

	@Option(names = { "-cid", "--customer-id" }, required = true, description = "The ID of the customer")
	private int cid;

	@Option(names = { "-n", "--name" }, description = "The name of the product")
	private String name;

	@Option(names = { "-s", "--size" }, required = true, description = "The size of the product")
	private Integer size;

	@Option(names = { "-st", "--special-treatment" }, description = "Special Treatment for the product")
	private Boolean specialTreatment;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			Product product = new Product();
			product.setCustomerId(cid);
			product.setName(name);
			product.setSpecialTreatment(specialTreatment);
			product.setSize(size);
			product.setStatus(null);

			product = this.addProduct(product);

			if (product != null)
				logger.info("Added " + product + " to the System.");

			em.close();
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
	}

	public Product addProduct(Product product) {
		try {
			em.getTransaction().begin();

			product.setCost(product.getSize() * 10f);
			em.persist(product);
			Product p = em.find(Product.class, getLastGeneratedId());

			List<Worker> workers = Worker.getAllWorkers();

			for (Worker w : workers) {

				if (w.getProductId() == null) {
					w.setProductId(p.getId());
					p.setStatus("In Treatment");
					em.merge(p);
					em.merge(w);
					break;
				}
			}
			if (p.getStatus() == null) {
				p.setStatus("Waiting");
				em.merge(p);
			}

			this.updateInvoice(p);
			em.getTransaction().commit();
			return p;

		} catch (Exception e) {
			em.getTransaction().rollback();
			return null;
		}

	}

	private void updateInvoice(Product product) {

		List<Invoice> invoises = Invoice.getAllInvoices();

		Boolean noInvoiceForCustomer = true;
		for (Invoice i : invoises) {
			Boolean existInvoice = i.getCustomerId().equals(product.getCustomerId()) && !i.getDelivered();

			if (Boolean.TRUE.equals(existInvoice)) {
				i.setCost(i.getCost() + product.getCost());
				em.merge(this.calcDiscount(i));
				noInvoiceForCustomer = false;
				break;
			}
		}
		if (Boolean.TRUE.equals(noInvoiceForCustomer)) {
			Invoice i = new Invoice();
			i.setCustomerId(product.getCustomerId());
			i.setCost(product.getCost());
			em.persist(this.calcDiscount(i));
		}
	}

	private Invoice calcDiscount(Invoice i) {

		Invoice newInvoice = i;
		Float discountRate = 0f;

		if (i.getCost() > 800)
			discountRate = 0.2f;
		else if (i.getCost() > 400)
			discountRate = 0.1f;
		else if (i.getCost() > 300)
			discountRate = 0.05f;

		Float dicountedCost = i.getCost() - (discountRate * i.getCost());

		newInvoice.setDiscountRate(discountRate);
		newInvoice.setDiscountedCost(dicountedCost);
		newInvoice.setDelivered(false);

		return newInvoice;

	}

	public int getLastGeneratedId() {
		Query query = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object result = query.getSingleResult();
		Long lastGeneratedId = ((BigInteger) result).longValue();
		return lastGeneratedId.intValue();
	}

}
