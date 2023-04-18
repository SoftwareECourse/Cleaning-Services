package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import app.commands.AddProductCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddProductSteps {

	Product product;
	AddProductCommand addProductCommand;
	EntityManager em;

	public AddProductSteps(AddProductCommand addProductCommand) {
		this.addProductCommand = addProductCommand;
	}

	@Given("I have a product with name {string} special treatment {string} for a customer with id {int}")
	public void i_have_a_product_with_name_special_treatment_for_a_customer_with_id(String name,
			String specialTreatment, int cid) {

		product = new Product();
		product.setName(name);
		product.setSpecialTreatment(Boolean.parseBoolean(specialTreatment));
		product.setCustomer_id(cid);
	}

	@When("the admin add the product to the database")
	public void the_admin_add_the_product_to_the_database() {
		addProductCommand.addProduct(product);
	}

	@Then("the product should not be saved in the database")
	public void the_product_should_not_be_saved_in_the_database() {
		em = App.getEntityManager();
		Product savedProdcut = em.find(Product.class, addProductCommand.getLastGeneratedId());

		assertNotEquals(product.getName(), savedProdcut.getName());
		assertNotEquals(product.getSpecialTreatment(), savedProdcut.getSpecialTreatment());
		assertNotEquals(product.getCustomer_id(), savedProdcut.getCustomer_id());

	}

	@Then("the product should be saved in the database with the correct information")
	public void the_product_should_be_saved_in_the_database_with_the_correct_information() {
		em = App.getEntityManager();
		Product savedProdcut = em.find(Product.class, addProductCommand.getLastGeneratedId());

		assertNotNull(savedProdcut);
		assertEquals(product.getName(), savedProdcut.getName());
		assertEquals(product.getSpecialTreatment(), savedProdcut.getSpecialTreatment());
		assertEquals(product.getCustomer_id(), savedProdcut.getCustomer_id());

	}

}
