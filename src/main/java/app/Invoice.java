package app;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

@Entity
public class Invoice {

	@Id
	int id;
	Integer customer_id;
	Float discountRate;
	Float cost;
	Float discountedCost;
	Boolean delivered;

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", customer_id=" + customer_id + ", discountRate=" + discountRate + ", cost="
				+ cost + ", discountedCost=" + discountedCost + ", delivered=" + delivered + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Float discountRate) {
		this.discountRate = discountRate;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getDiscountedCost() {
		return discountedCost;
	}

	public void setDiscountedCost(Float discountedCost) {
		this.discountedCost = discountedCost;
	}

	static EntityManager em = App.getEntityManager();

	public static Invoice findInvoice(int id) {
		return em.find(Invoice.class, id);
	}

	public static List<Invoice> getAllInvoices() {
		TypedQuery<Invoice> query = em.createQuery("FROM Invoice", Invoice.class);
		return query.getResultList();

	}

}
