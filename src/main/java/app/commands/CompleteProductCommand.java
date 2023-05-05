package app.commands;

import java.util.List;

import javax.persistence.EntityManager;

import app.Admin;
import app.App;
import app.Product;
import app.Worker;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "complete", description = "Mark a product as completed and available to the customer")
public class CompleteProductCommand implements Runnable {

	@Option(names = { "-p", "--product" }, required = true, description = "Product ID")
	private int productId;

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			if (this.setComplete(productId).getStatus().equals("Complete"))
				System.out.printf("Product %d has been marked as completed and is now available to the customer.%n",
						productId);
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}

	}

	public Product setComplete(int productId) {

		em.getTransaction().begin();
		Product p = em.find(Product.class, productId);

		if (p != null) {
			if (p.getStatus().equals("In Treatment")) {

				List<Worker> workers = Worker.getAllWorkers();
				for (Worker w : workers) {
					if (w.getProductId().equals(p.getId())) {
						em.merge(Worker.giveWork(w));
					}
				}
				p.setStatus("Complete");
				em.merge(p);
				em.getTransaction().commit();
			} else {
				if (p.getStatus().equals("Complete"))
					System.out.println("The product is already Complete");
				else
					System.out.println("The product is in Waiting state");
			}
			return p;
		} else {
			System.out.println("Product id is not correct");
			return null;
		}

	}

}