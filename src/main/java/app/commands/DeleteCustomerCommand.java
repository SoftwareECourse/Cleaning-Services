package app.commands;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Customer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "delete-customer", description = "Deletes a customer by ID.")
public class DeleteCustomerCommand implements Runnable {

	@Option(names = { "-i", "--id" }, required = true, description = "The ID of the customer to delete.")
	private int id;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			Customer customer = em.find(Customer.class, id);
			this.deleteCustomer(customer);
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}

	}

	public void deleteCustomer(Customer customer) {

		if (customer != null) {
			em.getTransaction().begin();
			customer = em.merge(customer);
			em.remove(customer);
			em.getTransaction().commit();
			em.close();
			logger.info("Deleting customer with ID: " + id);
		} else {
			logger.warn("There is no a customer with ID: " + id);
		}

	}

}