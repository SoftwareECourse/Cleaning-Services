package app.commands;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import app.Admin;
import app.App;
import app.Customer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add-customer", description = "Add a new customer to the system.")
public class AddCustomerCommand implements Runnable {

	@Parameters(index = "0", description = "The name of the customer.")
	private String name;

	@Parameters(index = "1", description = "The address of the customer.")
	private String address;

	@Parameters(index = "2", description = "The phone of the customer.")
	private int phone;

	@Parameters(index = "3", description = "The email of the customer.")
	private String email;

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			Customer customer = new Customer();

			customer.setName(name);
			customer.setAddress(address);
			customer.setPhone(phone);
			customer.setEmail(email);

			addCustomer(customer);
			customer.setId(getLastGeneratedId());

			System.out.println("Added " + customer + " to the System.");

			em.close();
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}
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
