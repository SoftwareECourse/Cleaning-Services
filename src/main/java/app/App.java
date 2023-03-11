package app;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.mapping.List;

import antlr.debug.NewLineEvent;
import app.commands.AddCustomerCommand;
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
				.addSubcommand("add-customer", new AddCustomerCommand());
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
