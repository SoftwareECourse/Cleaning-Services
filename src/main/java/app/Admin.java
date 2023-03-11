package app;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

	@Id
	private int id;
	private String password;
	private String username;
	private boolean state;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", state=" + state + "]";
	}

	public void setState(boolean b) {
		this.state = b;
	}

	public boolean getState() {
		return this.state;
	}

	public String getPassword() {
		return this.password;
	}
}
