package app;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import app.commands.DeleteCustomerCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteCustomerSteps {

	Customer customer;
	DeleteCustomerCommand deleteCustomerCommand;
	EntityManager em = App.getEntityManager();
	int id;

	public DeleteCustomerSteps(DeleteCustomerCommand deleteCustomerCommand) {
		this.deleteCustomerCommand = deleteCustomerCommand;
	}

	@Given("I have a customer with exist id and id equal to {int}")
	public void i_have_a_customer_with_exist_id_and_id_equal_to(Integer id) {
		this.id=id;
		customer = em.find(Customer.class, id);
	}

	@When("the admin enter the delete command")
	public void the_admin_enter_the_delete_command() {
		deleteCustomerCommand.deleteCustomer(customer);
	}

	@Then("the customer with id {int} should be deleted from the system")
	public void the_customer_with_id_should_be_deleted_from_the_system(Integer int1) {
		Customer c = em.find(Customer.class, id);
		assertNull(c); // if null then the customer deleted 
	}

}
