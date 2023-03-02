package app;

public class Admin {

	private String password;
	private boolean state;

	public Admin() {
		this.password = "admin123";
	}

	public void setState(boolean b) {
		this.state = b;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean getState() {
		return this.state;
	}

}
