package app.commands;

import java.util.List;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Customer;
import picocli.CommandLine.Command;

@Command(name = "list-customers", description = "List all customers")
public class ListCustomersCommand implements Runnable {

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			em.getTransaction().begin();

			List<Customer> customers = Customer.getAllCustomers();

			for (Customer c : customers) {
				logger.info(c);
			}

			em.close();
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
	}

}