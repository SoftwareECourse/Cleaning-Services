package app.commands;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import app.App;
import app.Customer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add-customer", description = "Add a new customer to the system.")
public class AddCustomerCommand implements Runnable {

	@Parameters(index = "0", description = "The name of the customer.")
	private String name;

	@Parameters(index = "1", description = "The name of the customer.")
	private String address;

	@Parameters(index = "2", description = "The email address of the customer.")
	private int phone;

	@Parameters(index = "3", description = "The email address of the customer.")
	private String email;

	EntityManager em = App.getEntityManager();

	public void run() {

		Customer customer = new Customer();

		customer.setId(0); // id will be auto inc in the database
		customer.setName(name);
		customer.setAddress(address);
		customer.setPhone(phone);
		customer.setEmail(email);

		addCustomer(customer);

		System.out.println("Added " + customer + " to the System" + getLastGeneratedId());

		em.close();
	}

	public void addCustomer(Customer customer) {
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
	}

	public int getLastGeneratedId() {

		Query query = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object result = query.getSingleResult();
		Long lastGeneratedId = ((BigInteger) result).longValue();
		return lastGeneratedId.intValue();
	}

}
