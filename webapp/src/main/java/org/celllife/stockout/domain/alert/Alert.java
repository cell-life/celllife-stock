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
import javax.persistence.OneToOne;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.user.User;

@Entity
@Cacheable
public class Alert implements Serializable {

	private static final long serialVersionUID = -6226563319400467361L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
	
	private Date date;
	
	private Integer level;
	
	private String message;
	
	@Enumerated(EnumType.STRING)
	private AlertStatus status;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	@OneToOne(fetch = FetchType.EAGER)
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
}
