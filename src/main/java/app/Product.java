package app;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

@Entity
public class Product {

	@Id
	int id;
	String name;
	String picture;
	String description;
	String status;
	Boolean specialTreatment;
	Integer customerId;
	Integer size;
	Float cost;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", picture=" + picture + ", description=" + description
				+ ", status=" + status + ", specialTreatment=" + specialTreatment + ", customer id=" + customerId + "]";
	}

	static EntityManager em = App.getEntityManager();

	public static Product findProduct(int id) {
		Product product = em.find(Product.class, id);
		em.close();
		return product;
	}

	public static List<Product> getAllProducts() {

		TypedQuery<Product> query = em.createQuery("FROM Product", Product.class);
		return query.getResultList();

	}

}
