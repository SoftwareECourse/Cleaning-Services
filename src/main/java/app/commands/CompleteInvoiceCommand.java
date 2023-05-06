package app.commands;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Customer;
import app.Email;
import app.Invoice;
import app.Product;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "complete-invoice", description = "Mark an invoice as complete")
public class CompleteInvoiceCommand implements Runnable {

	@Option(names = { "-i", "--invoice" }, required = true, description = "Invoice ID")
	private int invoiceId;

	@Option(names = { "-c", "--customer" }, required = false, description = "Customer ID")
	private int customerId;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			Invoice i = this.setComplete(invoiceId);
			if (i != null)
				logger.info("Invoice %d has been marked as complete.%n", invoiceId);

		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}

	}

	public Invoice setComplete(int invoiceId) {

		Invoice invoice = Invoice.findInvoice(invoiceId);
		Customer customer = Customer.findCustomer(invoice.getCustomerId());

		if (Boolean.FALSE.equals(invoice.getDelivered())) {

			Boolean invoiceIsReady = true;
			List<Product> products = Product.getAllProducts();
			for (Product c : products) {
				if (c.getCustomerId().equals(invoice.getCustomerId()) && !c.getStatus().equals("Complete")) {
					invoiceIsReady = false;
					break;
				}
			}

			if (Boolean.TRUE.equals(invoiceIsReady)) {
				em.getTransaction().begin();
				invoice.setDelivered(true);
				em.merge(invoice);
				em.getTransaction().commit();

				Email email = new Email();
				String message = String.format("Hello %s \nYour order is ready \nCost = %.1f \nDiscounted cost=%.1f",
						customer.getName(), invoice.getCost(), invoice.getDiscountedCost());
				email.sendEmail(customer.getEmail(), "Your order is ready", message);

				return invoice;
			} else {
				logger.info("The invoice is not ready, There is a products under treatment");
				return null;
			}

		} else {
			logger.warn("The invoice is already complete ");
			return null;
		}

	}

}