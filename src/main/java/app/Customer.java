package app;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

@Entity
public class Customer {

	@Id
	int id;
	String name;
	String address;
	String email;
	int phone;

	static EntityManager em = App.getEntityManager();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone="
				+ phone + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public static Customer findCustomer(int id) {
		return em.find(Customer.class, id);

	}

	public static List<Customer> getAllCustomers() {

		TypedQuery<Customer> query = em.createQuery("FROM Customer", Customer.class);
		return query.getResultList();
	}

}
