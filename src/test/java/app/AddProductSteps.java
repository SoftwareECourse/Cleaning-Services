package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import app.commands.AddProductCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddProductSteps {

	Product product;
	Product savedProduct;
	Boolean availableWorker = false;
	int availableWorkerId;
	AddProductCommand addProductCommand;
	EntityManager em;

	public AddProductSteps(AddProductCommand addProductCommand) {
		this.addProductCommand = addProductCommand;
	}

//	@Given("I have a product with name {string} and size {int} special treatment {string} for a customer with id {int}")
//	public void i_have_a_product_with_name_and_size_special_treatment_for_a_customer_with_id(String name, int size,
//			String specialTreatment, int cid) {
//
//		product = new Product();
//		product.setName(name);
//		product.setSize(size);
//		product.setSpecialTreatment(Boolean.parseBoolean(specialTreatment));
//		product.setCustomer_id(cid);
//	}

	@Given("I have a product with name {string} and size {int} special treatment {string}")
	public void i_have_a_product_with_name_and_size_special_treatment(String name, Integer size,
			String specialTreatment) {

		product = new Product();
		product.setName(name);
		product.setSize(size);
		product.setSpecialTreatment(Boolean.parseBoolean(specialTreatment));

		List<Worker> workers = Worker.getAllWorkers();
		for (Worker c : workers) {
			if (c.getProductId() == null) {
				availableWorker = true;
				availableWorkerId = c.getId();
				break;
			}
		}

	}

	@When("the admin add the product to the database for the last added customer")
	public void the_admin_add_the_product_to_the_database_for_the_last_added_customer() {
		// this step will be after the steps of adding customer so we need to get the
		// customer id

		List<Customer> customers = Customer.getAllCustomers();
		int customerId = customers.get(customers.size() - 1).getId();
		System.out.println(customerId);
		product.setCustomer_id(customerId);

		product = addProductCommand.addProduct(product);
	}

	// TODO check this step
	@Then("the product should not be saved in the database")
	public void the_product_should_not_be_saved_in_the_database() {
		em = App.getEntityManager();
		Product savedProdcut = em.find(Product.class, addProductCommand.getLastGeneratedId());

		assertNotEquals(product.getName(), savedProdcut.getName());
		assertNotEquals(product.getSpecialTreatment(), savedProdcut.getSpecialTreatment());
		assertNotEquals(product.getCustomer_id(), savedProdcut.getCustomer_id());

	}

	@Then("the product should be saved in the database with the correct information with cost {float}")
	public void the_product_should_be_saved_in_the_database_with_the_correct_information_with_cost(Float cost) {
		em = App.getEntityManager();
//		Product savedProdcut = em.find(Product.class, addProductCommand.getLastGeneratedId());

		List<Product> products = Product.getAllProducts();
		savedProduct = products.get(products.size() - 1); // last product

		assertNotNull(savedProduct);
		assertEquals(product.getName(), savedProduct.getName());
		assertEquals(product.getSpecialTreatment(), savedProduct.getSpecialTreatment());
		assertEquals(product.getCustomer_id(), savedProduct.getCustomer_id());
		assertEquals(product.getSize(), savedProduct.getSize());
		assertEquals(cost, savedProduct.getCost());

	}

	@Then("if there is an available worker, the product state should be {string} and the worker should take the product to work on")
	public void if_there_is_an_available_worker_the_product_state_should_be_and_the_worker_should_take_the_product_to_work_on(
			String status) {

		if (availableWorker) {
			assertEquals(status, savedProduct.getStatus());
			Worker worker = Worker.findWorker(availableWorkerId);
			assertEquals(worker.getProductId(), (Object) savedProduct.getId());
		} else {
			assertTrue(true);
		}

	}

	@Then("if no, the state of the product should be {string}")
	public void if_no_the_state_of_the_product_should_be(String status) {
		if (!availableWorker) {
			assertEquals(status, savedProduct.getStatus());
		} else {
			assertTrue(true);
		}
	}

	@Then("a new invoice will be created for the new customer without any discount")
	public void a_new_invoice_will_be_created_for_the_new_customer_without_any_discount() {

		List<Invoice> invoices = Invoice.getAllInvoices();
		List<Customer> customers = Customer.getAllCustomers();

		Invoice lastInvoice = invoices.get(invoices.size() - 1);
		Customer lastCustomer = customers.get(customers.size() - 1);

		assertEquals(lastInvoice.getCustomer_id(), (Object) lastCustomer.getId());
		assertEquals(lastInvoice.getCost(), (Object) product.getCost());
		assertEquals(lastInvoice.getCost(), (Object) lastInvoice.getDiscountedCost());
		assertEquals(lastInvoice.getDiscountRate(), (Object) 0f);
		assertFalse(lastInvoice.getDelivered());

	}

	@Then("the old invoice should has a cost equal to {float} and discounted cost equal to {float} and discount rate {float}")
	public void the_old_invoice_should_has_a_cost_equal_to_and_discounted_cost_equal_to_and_discount_rate(Float cost,
			Float discountedCost, Float discountRate) {

		List<Invoice> invoices = Invoice.getAllInvoices();
		Invoice lastInvoice = invoices.get(invoices.size() - 1);

		assertEquals(cost, lastInvoice.getCost());
		assertEquals(discountedCost, lastInvoice.getDiscountedCost());
		assertEquals(discountRate, lastInvoice.getDiscountRate());
		assertFalse(lastInvoice.getDelivered());

	}

}
