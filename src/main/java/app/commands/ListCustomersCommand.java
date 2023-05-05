package app.commands;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Customer;
import picocli.CommandLine.Command;

@Command(name = "list-customers", description = "List all customers")
public class ListCustomersCommand implements Runnable {

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

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
//			List<Customer> customers = query.list();9
//			

//			for(Customer c: customers) {
//				System.out.println(c);
//			}

//			Query query = em.createQuery("FROM Customer", Customer.class);
//			List<Customer> customers = query.getResultList();

			List<Customer> customers = Customer.getAllCustomers();

			for (Customer c : customers) {
				logger.info(c);
			}

			em.close();
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
	}

}