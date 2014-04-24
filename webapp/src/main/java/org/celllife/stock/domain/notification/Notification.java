package org.celllife.stock.domain.notification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.celllife.stock.domain.alert.Alert;

@Entity
@Cacheable
/**
 * Domain object to represent a Notification. Notifications are communication sent to the user and are generally
 * email or SMS. Notifications are associated with an Alert.
 */
public class Notification implements Serializable {

	private static final long serialVersionUID = 1289806776612459043L;

	@Id
    @TableGenerator(
            name="NotificationIdGen", 
            table="hibernate_sequences", 
            pkColumnName="sequence_name", 
            valueColumnName="sequence_next_hi_value", 
            pkColumnValue="notification",
            initialValue=1,
            allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="NotificationIdGen")
    private Long id;
	
	private Date date;
	private String message;
	private String recipient;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {})
	private Alert alert;
	
	public Notification() {
		
	}

	public Notification(Date date, String message, String recipient, Alert alert) {
		super();
		this.date = date;
		this.message = message;
		this.recipient = recipient;
		this.alert = alert;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
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
		Notification other = (Notification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", date=" + date + ", message=" + message + ", recipient=" + recipient
				+ ", alert=" + alert + "]";
	}
}
