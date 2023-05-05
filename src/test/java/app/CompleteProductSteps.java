package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import app.commands.CompleteProductCommand;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompleteProductSteps {

	CompleteProductCommand cpd;
	EntityManager em;
	Product productAfterComplete;
	Product productBeforeComplete;

	public CompleteProductSteps(CompleteProductCommand cpd) {
		this.cpd = cpd;
	}

//	@Given("I have a product with id {int}")
//	public void i_have_a_product_with_id(Integer id) {
//		product = new Product();
//		product.setId(id);
//	}

	@When("The admin run the complete product command")
	public void the_admin_run_the_complete_product_command() {
		List<Product> products = Product.getAllProducts();
		productBeforeComplete = products.get(products.size() - 1);

		productAfterComplete = cpd.setComplete(productBeforeComplete.getId());
	}

	@Then("The status of the product will be {string}")
	public void the_status_of_the_product_will_be(String status) {

		if (productBeforeComplete.getStatus().equals("In Treatment"))
			assertEquals(status, productAfterComplete.getStatus());
		else
			assertTrue(true);
	}

	@Then("if the product is in the {string} state it should still on it")
	public void if_the_product_is_in_the_state_it_should_still_on_it(String status) {

		if (productBeforeComplete.getStatus().equals(status))
			assertEquals(status, productAfterComplete.getStatus());
		else
			assertTrue(true);
	}

}
