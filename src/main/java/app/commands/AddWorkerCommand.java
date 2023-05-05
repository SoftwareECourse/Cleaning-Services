package app.commands;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Worker;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add-worker", description = "Add a new worker to the system.")
public class AddWorkerCommand implements Runnable {

	@Parameters(index = "0", description = "The name of the worker.")
	private String name;

	@Parameters(index = "1", description = "The address of the worker.")
	private String address;

	@Parameters(index = "2", description = "The phone of the worker.")
	private int phone;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			Worker worker = new Worker();

			worker.setName(name);
			worker.setAddress(address);
			worker.setPhone(phone);

			addWorker(worker);
			worker.setId(getLastGeneratedId());

			logger.info("Added " + worker + " to the System.");

			em.close();
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
	}

	public void addWorker(Worker worker) {
		em.getTransaction().begin();
		Worker w = Worker.giveWork(worker);
		em.persist(w);
		em.getTransaction().commit();
	}

	public int getLastGeneratedId() {

		Query query = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object result = query.getSingleResult();
		Long lastGeneratedId = ((BigInteger) result).longValue();
		return lastGeneratedId.intValue();
	}

}
