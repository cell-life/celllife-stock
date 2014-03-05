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
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.user.User;

@Entity
@Cacheable
/**
 * Domain object to represent Stock in the system - type (@see StockType) is either a stock take or a stock arrival
 * and is associated with a specific User and is for a specific Drug
 */
public class Stock implements Serializable {

	private static final long serialVersionUID = -815967956156971026L;

	@Id
    @TableGenerator(
            name="StockIdGen", 
            table="hibernate_sequences", 
            pkColumnName="sequence_name", 
            valueColumnName="sequence_next_hi_value", 
            pkColumnValue="stock")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="StockIdGen")
    private Long id;
	
	private Date date;
	private Integer quantity;
	
	@Enumerated(EnumType.STRING)
	private StockType type;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {})
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {})
	private Drug drug;
	
	public Stock() {
		
	}

	public Stock(Date date, Integer quantity, StockType type, User user, Drug drug) {
		super();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
