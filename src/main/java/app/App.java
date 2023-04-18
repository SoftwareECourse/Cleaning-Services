package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import app.commands.AddCustomerCommand;
import app.commands.AddProductCommand;
import app.commands.AddWorkerCommand;
import app.commands.DeleteCustomerCommand;
import app.commands.DeleteWorkerCommand;
import app.commands.ListCustomersCommand;
import app.commands.ListWorkersCommand;
import app.commands.LoginCommand;
import app.commands.LogoutCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "app", mixinStandardHelpOptions = true, version = "1.0", description = "Cleaning services for carpets and covers")
public class App implements Runnable {

	static CommandLine commandLine;

	public static void main(String[] args) {

		commandLine = new CommandLine(new App());
		commandLine.addSubcommand("login", new LoginCommand()).addSubcommand("logout", new LogoutCommand())
				.addSubcommand("add-customer", new AddCustomerCommand())
				.addSubcommand("delete-customer", new DeleteCustomerCommand())
				.addSubcommand("add-worker", new AddWorkerCommand())
				.addSubcommand("delete-worker", new DeleteWorkerCommand())
				.addSubcommand("list-customers", new ListCustomersCommand())
				.addSubcommand("list-workers", new ListWorkersCommand())
				.addSubcommand("add-product", new AddProductCommand());

		commandLine.execute(args);

	}

	public void run() {
		System.out.println("Welcome to Cleaning services for carpets and covers!");
	}

	public static EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
		EntityManager em = emf.createEntityManager();
		return em;
	}

}
