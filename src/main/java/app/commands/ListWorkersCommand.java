package app.commands;

//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
import java.util.List;

import javax.persistence.EntityManager;

import app.Admin;
import app.App;
import app.Worker;
import picocli.CommandLine.Command;

@Command(name = "list-workers", description = "List all workers")
public class ListWorkersCommand implements Runnable {

	EntityManager em = App.getEntityManager();

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {

			em.getTransaction().begin();

//			Query query = em.createQuery("FROM Worker", Worker.class);
//			List<Worker> workers = query.getResultList();

			List<Worker> workers = Worker.getAllWorkers();

			for (Worker w : workers) {
				System.out.println(w);
			}

			em.close();
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}
	}

}