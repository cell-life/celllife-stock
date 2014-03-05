package org.celllife.stockout.domain.drug;

import java.io.Serializable;

public class DrugDto implements Serializable {

	private static final long serialVersionUID = 5614601761140919135L;

    private Long id;
	
	private String barcode;
	
	private String description;
	
	public DrugDto() {
		
	}

	public DrugDto(Drug drug) {
		super();
		this.id = drug.getId();
		this.barcode = drug.getBarcode();
		this.description = drug.getDescription();
	}

	public DrugDto(String barcode, String description) {
		super();
		this.barcode = barcode;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
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
		DrugDto other = (DrugDto) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Drug [id=" + id + ", barcode=" + barcode + ", description=" + description + "]";
	}
}
