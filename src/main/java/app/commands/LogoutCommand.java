package app.commands;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import picocli.CommandLine.Command;

@Command(name = "logout", description = "logout from the Cleaing System")
public class LogoutCommand implements Runnable {

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		Admin admin = getAdmin();
		em.getTransaction().begin();

		if (admin.getState()) {
			admin.setState(false);
			em.merge(admin);
			em.getTransaction().commit();
			em.close();
			logger.info("Logged out from System.");
		} else {
			logger.warn("You already logged out from the System");
		}

	}

	public Admin getAdmin() {
//		TODO You should get all admins and check all
		return em.find(Admin.class, 1);

	}

}
