package app.commands;

import javax.persistence.EntityManager;

import app.Admin;
import app.App;
import picocli.CommandLine.Command;

@Command(name = "logout", description = "logout from the Cleaing System")
public class LogoutCommand implements Runnable {

	EntityManager em = App.getEntityManager();

	public void run() {

		Admin admin = getAdmin();
		em.getTransaction().begin();

		if (admin.getState()) {
			admin.setState(false);
			em.merge(admin);
			em.getTransaction().commit();
			em.close();
			System.out.println("Logged out from System.");
		} else {
			System.out.println("You already logged out from the System");
		}

	}

	public Admin getAdmin() {
//		TODO You should get all admins and check all
		Admin admin = em.find(Admin.class, 1);
		return admin;
	}

}
