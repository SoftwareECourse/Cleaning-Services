package app;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	int id;
	String name;
	String picture;
	String description;
	String status;
	Boolean specialTreatment;
	Integer customer_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getSpecialTreatment() {
		return specialTreatment;
	}

	public void setSpecialTreatment(Boolean specialTreatment) {
		this.specialTreatment = specialTreatment;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", picture=" + picture + ", description=" + description
				+ ", status=" + status + ", specialTreatment=" + specialTreatment + ", customer_id=" + customer_id
				+ "]";
	}

	static EntityManager em = App.getEntityManager();

	public static Product findProduct(int id) {
		Product product = em.find(Product.class, id);
		em.close();
		return product;
	}

}
