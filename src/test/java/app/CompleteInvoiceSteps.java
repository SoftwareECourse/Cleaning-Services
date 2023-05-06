package app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import app.commands.CompleteInvoiceCommand;
import app.commands.CompleteProductCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompleteInvoiceSteps {

	CompleteInvoiceCommand completeInvoiceCommand;
	EntityManager em;

	public CompleteInvoiceSteps(CompleteInvoiceCommand cic) {
		this.completeInvoiceCommand = cic;
	}

	@When("the admin run the complete invoice command for the last added invoice")
	public void the_admin_run_the_complete_invoice_command() {
		List<Invoice> invoices = Invoice.getAllInvoices();
		Invoice lastInvoice = invoices.get(invoices.size() - 1);

		completeInvoiceCommand.setComplete(lastInvoice.getId());

	}

	@Then("the invoice will not be complete and no email sent to the customer")
	public void the_invoice_will_not_be_complete_and_no_email_sent_to_the_customer() {

		List<Invoice> invoices = Invoice.getAllInvoices();
		Invoice lastInvoice = invoices.get(invoices.size() - 1);

		assertFalse(lastInvoice.getDelivered());
	}

	@Given("that the last invoice products all are complete")
	public void that_the_last_invoice_products_all_are_complete() {

		em = App.getEntityManager();
		em.getTransaction().begin();

		List<Invoice> invoices = Invoice.getAllInvoices();
		List<Product> products = Product.getAllProducts();
		Invoice lastInvoice = invoices.get(invoices.size() - 1);

		CompleteProductCommand cpc = new CompleteProductCommand();

		for (Product p : products) {
			if (lastInvoice.getCustomerId().equals(p.getCustomerId())) {
				if (p.getStatus().equals("In Treatment")) {
					cpc.setComplete(p.getId());
				}
				p.setStatus("Complete");
				em.merge(p);
			}
		}
		em.getTransaction().commit();

	}

	@Then("the invoice will be complete and email sent to the customer")
	public void the_invoice_will_be_complete_and_email_sent_to_the_customer() {
		List<Invoice> invoices = Invoice.getAllInvoices();
		Invoice lastInvoice = invoices.get(invoices.size() - 1);

		assertTrue(lastInvoice.getDelivered());

	}

}
