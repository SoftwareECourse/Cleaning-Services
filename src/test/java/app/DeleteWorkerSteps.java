package app;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import app.commands.DeleteWorkerCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteWorkerSteps {

	Worker worker;
	DeleteWorkerCommand deleteWorkerCommand;
	EntityManager em = App.getEntityManager();
	int id;

	public DeleteWorkerSteps(DeleteWorkerCommand deleteWorkerCommand) {
		this.deleteWorkerCommand = deleteWorkerCommand;
	}

	@Given("I have a worker with exist id and id equal to {int}")
	public void i_have_a_worker_with_exist_id_and_id_equal_to(Integer id) {
		this.id = id;
		worker = em.find(Worker.class, id);
	}

	@When("the admin enter the worker delete command")
	public void the_admin_enter_the_worker_delete_command() {
		deleteWorkerCommand.deleteWorker(em, worker);
	}

	@Then("the worker with id {int} should be deleted from the system")
	public void the_worker_with_id_should_be_deleted_from_the_system(Integer int1) {

		Worker c = em.find(Worker.class, id);
		assertNull(c); // if null then the customer deleted
		em.close();
	}

}
