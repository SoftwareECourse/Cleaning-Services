package app;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import app.commands.LoginCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	Admin admin;
	LoginCommand logincommand;
	EntityManager em = App.getEntityManager();

	// Dep injection
	public LoginSteps(LoginCommand login) {
		this.logincommand = login;
	}

	@Given("that Admin is not logged in")
	public void that_admin_is_not_logged_in() {
		em.getTransaction().begin();
		admin = logincommand.getAdmin();
		admin.setState(false);
		em.merge(admin);
		em.getTransaction().commit();
	}

	@When("the enterd password is {string} and the username is {string}")
	public void the_enterd_password_is_and_the_username_is(String password, String username) {
		logincommand.setPassword(password);
		logincommand.setUsername(username);
		logincommand.run();
	}

	@Then("the admin logged in succssfuly")
	public void the_admin_logged_in_succssfuly() {
		assertTrue(this.admin.getState() == true);
	}

	@Then("the admin login fails")
	public void the_admin_login_fails() {
		assertTrue(this.admin.getState() == false);
	}

//
//	String enterdPassword;
//	Admin admin;
//
//	public LoginSteps(Admin admin) {
//		this.admin = admin;
//	}
//
//	@Given("that Admin is not logged in")
//	public void that_admin_is_not_logged_in() {
//		// admin state is false = no logged in
//		this.admin.setState(false);
//	}
//
//	@When("the enterd password is {string}")
//	public void the_enterd_password_is(String password) {
//		this.enterdPassword = password;
//		if (this.admin.getPassword().equals(password))
//			this.admin.setState(true);
//		else
//			this.admin.setState(false);
//	}
//
//	@Then("the admin logged in succssfuly")
//	public void the_admin_logged_in_succssfuly() {
//		assertTrue(this.admin.getState() == true);
//	}
//
//	@Then("the admin login fails")
//	public void the_admin_login_fails() {
//		assertTrue(this.admin.getState() == false);
//
//	}

}
