package app.commands;

import javax.persistence.EntityManager;

import app.Admin;
import app.App;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "login", description = "login the Cleaing System")
public class LoginCommand implements Runnable {

	@Option(names = { "-u", "--username" }, description = "The name of the admin", required = true)
	private String username;

	@Option(names = { "-p", "--passowrd" }, description = "The password of the admin", required = true)
	private String password;

	EntityManager em = App.getEntityManager();

	public void run() {

		Admin admin = getAdmin(); // get the admin from the database
		em.getTransaction().begin();

		if (!admin.getState()) {
			if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
				admin.setState(true);
				em.merge(admin);
				em.getTransaction().commit();
				em.close();
				System.out.println("loged in the system");

			} else {
				System.out.println("please return the login step");
			}
		} else {
			System.out.println("You are alerady logged in maan");
		}

	}

	public Admin getAdmin() {
//		TODO You should get all admins and check all
		Admin admin = em.find(Admin.class, 1);
		return admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
