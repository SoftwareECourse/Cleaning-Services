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
	Integer customerId;
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
		return "Invoice [id=" + id + ", customer id=" + customerId + ", discountRate=" + discountRate + ", cost=" + cost
				+ ", discountedCost=" + discountedCost + ", delivered=" + delivered + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
