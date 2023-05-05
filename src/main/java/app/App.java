package app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import app.commands.AddCustomerCommand;
import app.commands.AddProductCommand;
import app.commands.AddWorkerCommand;
import app.commands.CompleteInvoiceCommand;
import app.commands.CompleteProductCommand;
import app.commands.DeleteCustomerCommand;
import app.commands.DeleteWorkerCommand;
import app.commands.ListCustomerProductsCommand;
import app.commands.ListCustomersCommand;
import app.commands.ListInvoicesCommand;
import app.commands.ListProductsCommand;
import app.commands.ListWorkersCommand;
import app.commands.LoginCommand;
import app.commands.LogoutCommand;
import app.commands.SendEmailCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "app", mixinStandardHelpOptions = true, version = "1.0", description = "Cleaning services for carpets and covers")
public class App implements Runnable {

	static CommandLine commandLine;
	public static Logger logger = LogManager.getLogger(App.class.getName());

	public static void main(String[] args) {

		commandLine = new CommandLine(new App());

		commandLine.addSubcommand("login", new LoginCommand()).addSubcommand("logout", new LogoutCommand())
				.addSubcommand("add-customer", new AddCustomerCommand())
				.addSubcommand("delete-customer", new DeleteCustomerCommand())
				.addSubcommand("add-worker", new AddWorkerCommand())
				.addSubcommand("delete-worker", new DeleteWorkerCommand())
				.addSubcommand("list-customers", new ListCustomersCommand())
				.addSubcommand("list-workers", new ListWorkersCommand())
				.addSubcommand("add-product", new AddProductCommand())
				.addSubcommand("list-products", new ListProductsCommand())
				.addSubcommand("lcp", new ListCustomerProductsCommand())
				.addSubcommand("list-customer-products", new ListCustomerProductsCommand())
				.addSubcommand("complete", new CompleteProductCommand())
				.addSubcommand("list-invoices", new ListInvoicesCommand())
				.addSubcommand("send-email", new SendEmailCommand())
				.addSubcommand("complete-invoice", new CompleteInvoiceCommand());
		commandLine.execute(args);

	}

	public void run() {
		logger.info("Welcome to Cleaning services for carpets and covers!");
	}

	public static EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
		EntityManager em = emf.createEntityManager();
		return em;
	}

}
