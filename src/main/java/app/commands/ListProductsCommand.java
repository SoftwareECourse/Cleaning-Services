package app.commands;

import java.util.List;

import javax.persistence.EntityManager;

import app.Admin;
import app.App;
import app.Product;
import picocli.CommandLine.Command;

@Command(name = "list-products", description = "List all products")
public class ListProductsCommand implements Runnable {

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			em.getTransaction().begin();

//			Query query = em.createQuery("FROM Product", Product.class);
//			List<Product> products = query.getResultList();

			List<Product> products = Product.getAllProducts();

			for (Product p : products) {
				System.out.println(p);
			}

			em.close();

		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");

		}

	}

}
