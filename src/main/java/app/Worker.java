package app;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

@Entity
public class Worker {

	@Id
	int id;
	String name;
	String address;
	Integer phone;
	Integer productId; // the id of the product which the worker is working on it

	static EntityManager em = App.getEntityManager();

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", productId="
				+ productId + "]";
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

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Worker findWorker(int id) {
		Worker worker = em.find(Worker.class, id);
		em.close();
		return worker;
	}
}
