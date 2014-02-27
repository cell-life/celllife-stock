package org.celllife.stockout.domain.stock;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.user.User;

@Entity
@Cacheable
public class Stock implements Serializable {

	private static final long serialVersionUID = -815967956156971026L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
	
	private Date date;
	private Integer quantity;
	
	@Enumerated(EnumType.STRING)
	private StockType type;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Drug drug;
	
	public Stock() {
		
	}

	public Stock(Long id, Date date, Integer quantity, StockType type, User user, Drug drug) {
		super();
		this.id = id;
		this.date = date;
		this.quantity = quantity;
		this.type = type;
		this.user = user;
		this.drug = drug;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", date=" + date + ", quantity=" + quantity + ", type=" + type + ", user=" + user
				+ ", drug=" + drug + "]";
	}

	
	
}
