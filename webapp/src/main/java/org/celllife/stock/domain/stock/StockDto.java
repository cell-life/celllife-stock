package org.celllife.stock.domain.stock;

import java.io.Serializable;
import java.util.Date;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugDto;
import org.celllife.stock.domain.user.ClinicDto;
import org.celllife.stock.domain.user.User;

public class StockDto implements Serializable {

	private static final long serialVersionUID = -815967956156971026L;

    private Long id;
	
	private Date date;
	private Integer quantity;
	private StockType type;
	private ClinicDto user;
	private DrugDto drug;
	
	public StockDto() {
		
	}
	
	public StockDto(Stock stock) {
		super();
		this.id = stock.getId();
		this.date = stock.getDate();
		this.quantity = stock.getQuantity();
		this.type = stock.getType();
		this.user = new ClinicDto(stock.getUser());
		this.drug = new DrugDto(stock.getDrug());		
	}

	public StockDto(Date date, Integer quantity, StockType type, User user, Drug drug) {
		super();
		this.date = date;
		this.quantity = quantity;
		this.type = type;
		this.user = new ClinicDto(user);
		this.drug = new DrugDto(drug);
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

	public ClinicDto getUser() {
		return user;
	}

	public void setUser(ClinicDto clinic) {
		this.user = clinic;
	}

	public DrugDto getDrug() {
		return drug;
	}

	public void setDrug(DrugDto drug) {
		this.drug = drug;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", date=" + date + ", quantity=" + quantity + ", type=" + type + ", clinic=" + user
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
		StockDto other = (StockDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
