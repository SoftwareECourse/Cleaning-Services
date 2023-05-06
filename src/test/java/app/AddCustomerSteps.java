package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import app.commands.AddCustomerCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddCustomerSteps {

	int numberOfCustomers;
	Customer customer;
	AddCustomerCommand addCustomerCommand;
	EntityManager em = App.getEntityManager();

	// dep inj
	public AddCustomerSteps(AddCustomerCommand addCustomerCommand) {
		this.addCustomerCommand = addCustomerCommand;
	}

	@Given("I have a customer with name {string},address {string} , phone {int} and email {string}")
	public void i_have_a_customer_with_name_address_phone_and_email(String name, String address, int phone,
			String email) {

		customer = new Customer();

		// id will be auto inc in the database
		customer.setName(name);
		customer.setAddress(address);
		customer.setPhone(phone);
		customer.setEmail(email);

		numberOfCustomers = Customer.getAllCustomers().size();

	}

	@When("the admin add the customer to the database")
	public void i_the_admin_add_the_customer_to_the_database() {
		addCustomerCommand.addCustomer(customer);
	}

	@Then("the customer should be saved in the database with the correct information")
	public void the_customer_should_be_saved_in_the_database_with_the_correct_information() {

		Customer savedCustomer = em.find(Customer.class, addCustomerCommand.getLastGeneratedId());

		assertNotNull(savedCustomer);
		assertEquals((numberOfCustomers + 1), Customer.getAllCustomers().size());
		assertEquals(customer.getName(), savedCustomer.getName());
		assertEquals(customer.getAddress(), savedCustomer.getAddress());
		assertEquals(customer.getPhone(), savedCustomer.getPhone());
		assertEquals(customer.getEmail(), savedCustomer.getEmail());
	}

}
