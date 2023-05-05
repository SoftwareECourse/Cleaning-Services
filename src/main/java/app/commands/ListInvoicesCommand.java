package app.commands;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Invoice;
import picocli.CommandLine.Command;

@Command(name = "list-invoices", description = "List all invoices")
public class ListInvoicesCommand implements Runnable {

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			em.getTransaction().begin();

			List<Invoice> in = Invoice.getAllInvoices();

			for (Invoice i : in) {
				logger.info(i);
			}

			em.close();
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
	}

}