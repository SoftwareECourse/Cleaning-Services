package app;

import static org.junit.Assert.assertTrue;

import app.Admin;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	String enterdPassword;
	Admin admin;

	public LoginSteps(Admin admin) {
		this.admin = admin;
	}

	@Given("that Admin is not logged in")
	public void that_admin_is_not_logged_in() {
		// admin state is false = no logged in
		this.admin.setState(false);
	}

	@When("the enterd password is {string}")
	public void the_enterd_password_is(String password) {
		this.enterdPassword = password;
		if (this.admin.getPassword().equals(password))
			this.admin.setState(true);
		else
			this.admin.setState(false);
	}

	@Then("the admin logged in succssfuly")
	public void the_admin_logged_in_succssfuly() {
		assertTrue(this.admin.getState() == true);
	}

	@Then("the admin login fails")
	public void the_admin_login_fails() {
		assertTrue(this.admin.getState() == false);

	}

}
