package app.commands;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Product;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list-customer-products", description = "List all products", mixinStandardHelpOptions = true)
public class ListCustomerProductsCommand implements Runnable {

	@Option(names = { "-cid", "--customer-id" }, required = true, description = "The ID of the customer")
	private int cid;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			em.getTransaction().begin();

			List<Product> products = Product.getAllProducts();

			for (Product p : products) {
				if (p.getCustomer_id() == cid)
					logger.info(p);
			}

			em.close();

		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}

	}

}
