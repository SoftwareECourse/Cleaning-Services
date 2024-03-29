package app.commands;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import app.Admin;
import app.App;
import app.Worker;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "delete-worker", description = "Deletes a worker by ID.")
public class DeleteWorkerCommand implements Runnable {

	@Option(names = { "-i", "--id" }, required = true, description = "The ID of the worker to delete.")
	private int id;

	EntityManager em = App.getEntityManager();
	Logger logger = App.logger;

	public void run() {

		LoginCommand logincommand = new LoginCommand();
		Admin admin = logincommand.getAdmin();

		if (admin.getState()) {
			Worker worker = em.find(Worker.class, id);
			this.deleteWorker(em, worker);
		} else {
			logger.warn("You can't run any command before logging in to the System, please login first.");
		}
		em.close();
	}

	public void deleteWorker(EntityManager em, Worker worker) {

		if (worker != null) {
			em.getTransaction().begin();
			worker = em.merge(worker);
			em.remove(worker);
			em.getTransaction().commit();

			logger.info("Deleting worker with ID: " + id);
		} else {
			logger.warn("There is no a worker with ID: " + id);
		}

	}

}