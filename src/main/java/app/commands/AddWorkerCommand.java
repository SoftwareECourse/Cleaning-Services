package app.commands;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			Worker worker = new Worker();

			worker.setName(name);
			worker.setAddress(address);
			worker.setPhone(phone);
			worker.setProductId(null);

			addWorker(worker);
			worker.setId(getLastGeneratedId());

			System.out.println("Added " + worker + " to the System.");

			em.close();
		} else {
			System.out.println("You can't run any command before logging in to the System, please login first.");
		}
	}

	public void addWorker(Worker worker) {
		em.getTransaction().begin();
		em.persist(worker);
		em.getTransaction().commit();
	}

	public int getLastGeneratedId() {

		Query query = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Object result = query.getSingleResult();
		Long lastGeneratedId = ((BigInteger) result).longValue();
		return lastGeneratedId.intValue();
	}

}
