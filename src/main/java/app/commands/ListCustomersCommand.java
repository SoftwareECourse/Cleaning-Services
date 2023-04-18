package app.commands;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import app.Admin;
import app.App;
import app.Customer;
import picocli.CommandLine.Command;

@Command(name = "list-customers", description = "List all customers")
public class ListCustomersCommand implements Runnable {

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			em.getTransaction().begin();

//			Configuration cfgg = new Configuration();
//			cfgg.configure("hibernate.cfg.xml");
//			SessionFactory sf = cfgg.buildSessionFactory();
//			Session session = sf.openSession();
//
//			Query<Customer> query = session.createQuery("from Customer", Customer.class);
//			List<Customer> customers = query.list();
//			

//			for(Customer c: customers) {
//				System.out.println(c);
//			}

			Query query = em.createQuery("FROM Customer", Customer.class);
			List<Customer> customers = query.getResultList();

			for (Customer c : customers) {
				System.out.println(c);
			}

			em.close();
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}
	}

}