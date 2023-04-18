package app.commands;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import app.Admin;
import app.App;
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

	@Option(names = { "-st", "--special-treatment" }, description = "Special Treatment for the product")
	private Boolean specialTreatment;

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			Product product = new Product();
			product.setCustomer_id(cid);
			product.setName(name);
			product.setSpecialTreatment(specialTreatment);
			product.setStatus(null);

			product = this.addProduct(product);

			if (product != null)
				System.out.println("Added " + product + " to the System.");

			em.close();
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}
	}

	public Product addProduct(Product product) {
		try {
			em.getTransaction().begin();

			em.persist(product);
			Product p = em.find(Product.class, getLastGeneratedId());

			Query query = em.createQuery("FROM Worker", Worker.class); // get all workers
			List<Worker> workers = query.getResultList();

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

			em.getTransaction().commit();
			return p;

		} catch (Exception e) {
			System.out.println("The customer id is not correct");
			return null;
		}

	}

	public int getLastGeneratedId() {
		Query query = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object result = query.getSingleResult();
		Long lastGeneratedId = ((BigInteger) result).longValue();
		return lastGeneratedId.intValue();
	}

}
