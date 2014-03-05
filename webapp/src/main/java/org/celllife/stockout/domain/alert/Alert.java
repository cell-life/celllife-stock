package org.celllife.stockout.domain.alert;

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
 * Domain object to represent Alerts in the system - they are associated with Users and are for a specific Drug.
 * Alerts have a level indicating importance and a status {@see AlertStatus} to indicate the lifecycle of the Alert
 */
public class Alert implements Serializable {

	private static final long serialVersionUID = -6226563319400467361L;

	@Id
    @TableGenerator(
            name="AlertIdGen", 
            table="hibernate_sequences", 
            pkColumnName="sequence_name", 
            valueColumnName="sequence_next_hi_value", 
            pkColumnValue="alert",
            initialValue=1,
            allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AlertIdGen")
    private Long id;
	
	private Date date;
	
	private Integer level;
	
	private String message;
	
	@Enumerated(EnumType.STRING)
	private AlertStatus status;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={})
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={})
	private Drug drug;
	
	public Alert() {
		
	}
	
	public Alert(Date date, Integer level, String message, AlertStatus status, User user, Drug drug) {
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
		Alert other = (Alert) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
