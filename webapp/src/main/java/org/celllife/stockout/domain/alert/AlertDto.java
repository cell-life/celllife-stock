package org.celllife.stockout.domain.alert;

import java.io.Serializable;
import java.util.Date;

import org.celllife.stockout.domain.drug.DrugDto;
import org.celllife.stockout.domain.user.UserDto;

public class AlertDto implements Serializable {

	private static final long serialVersionUID = 7075188646105441127L;

	private Long id;
	
	private Date date;
	
	private Integer level;
	
	private String message;
	
	private AlertStatus status;
	
	private UserDto user;
	
	private DrugDto drug;
	
	public AlertDto() {
	}

	public AlertDto(Alert alert) {
		super();
		this.id = alert.getId();
		this.date = alert.getDate();
		this.level = alert.getLevel();
		this.message = alert.getMessage();
		this.status = alert.getStatus();
		this.user = new UserDto(alert.getUser());
		this.drug = new DrugDto(alert.getDrug());		
	}
	
	public AlertDto(Date date, Integer level, String message, AlertStatus status, UserDto user, DrugDto drug) {
		super();
		this.date = date;
		this.level = level;
		this.message = message;
		this.status = status;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlertStatus getStatus() {
		return status;
	}

	public void setStatus(AlertStatus status) {
		this.status = status;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public DrugDto getDrug() {
		return drug;
	}

	public void setDrug(DrugDto drug) {
		this.drug = drug;
	}

	@Override
	public String toString() {
		return "Alert [id=" + id + ", date=" + date + ", level=" + level + ", message=" + message + ", status="
				+ status + ", user=" + user + ", drug=" + drug + "]";
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
		AlertDto other = (AlertDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
