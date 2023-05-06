package app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import app.commands.LogoutCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogoutSteps {

	Admin admin;
	LogoutCommand logoutcommand;
	EntityManager em = App.getEntityManager();

	// Dep injection
	public LogoutSteps(LogoutCommand logout) {
		this.logoutcommand = logout;
	}

	@Given("that admin is not loged out")
	public void that_admin_is_not_loged_out() {
		em.getTransaction().begin();
		admin = logoutcommand.getAdmin();
		admin.setState(true);
		em.merge(admin);
		em.getTransaction().commit();
		em.close();
	}

	@When("the admin enter the logout command")
	public void the_admin_enter_the_logout_command() {
		this.logoutcommand.run();
	}

	@Then("the user will log out from the system successfully")
	public void the_user_will_log_out_from_the_system_successfully() {
		assertFalse(this.admin.getState());
	}
}
