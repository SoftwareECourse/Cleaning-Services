package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import app.commands.AddWorkerCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddWorkerSteps {

	Worker worker;
	AddWorkerCommand addWorkerCommand;
	EntityManager em = App.getEntityManager();

	// dep inj
	public AddWorkerSteps(AddWorkerCommand addWorkerCommand) {
		this.addWorkerCommand = addWorkerCommand;
	}

	@Given("I have a worker with name {string},address {string} , phone {int}")
	public void i_have_a_worker_with_name_address_phone(String name, String address, Integer phone) {

		worker = new Worker();

		// id will be auto inc in the database
		worker.setName(name);
		worker.setAddress(address);
		worker.setPhone(phone);

	}

	@When("the admin add the worker to the database")
	public void the_admin_add_the_worker_to_the_database() {
		addWorkerCommand.addWorker(worker);
	}

	@Then("the worker should be saved in the database with the correct information")
	public void the_worker_should_be_saved_in_the_database_with_the_correct_information() {

		Worker savedWorker = em.find(Worker.class, addWorkerCommand.getLastGeneratedId());

		assertNotNull(savedWorker);
		assertEquals(worker.getName(), savedWorker.getName());
		assertEquals(worker.getAddress(), savedWorker.getAddress());
		assertEquals(worker.getPhone(), savedWorker.getPhone());

	}

}
