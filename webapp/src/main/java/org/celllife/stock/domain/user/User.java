package org.celllife.stock.domain.user;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@Cacheable
/**
 * Domain object to represent the User in the system - have a unique msisdn and belong to a clinic
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1963884212116602873L;

	@Id
    @TableGenerator(
            name="UserIdGen", 
            table="hibernate_sequences", 
            pkColumnName="sequence_name", 
            valueColumnName="sequence_next_hi_value", 
            pkColumnValue="user",
            initialValue=1,
            allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="UserIdGen")
    private Long id;
	
	private String msisdn;
	
	private String encryptedPassword;
	
	private String salt;
	
	private String clinicCode;
	
	private String clinicName;
	
	private String coordinates;

	private String pharmacistName;

	private String pharmacistMsisdn;
	
	private String clinicPhoneNumber;
	
	private String districtManagerEmail;

	private Integer safetyLevel;
	
	private Integer leadTime;
	
	@Column(columnDefinition = "BIT", length = 1)
	private Boolean activated;
	
	public User() {
		this.activated = Boolean.FALSE;
	}

	public User(String msisdn, String encryptedPassword, String salt, String clinicCode, String clinicName) {
		super();
		this.msisdn = msisdn;
		this.encryptedPassword = encryptedPassword;
		this.salt = salt;
		this.clinicCode = clinicCode;
		this.clinicName = clinicName;
		this.activated = Boolean.FALSE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getPharmacistMsisdn() {
        return pharmacistMsisdn;
    }

    public void setPharmacistMsisdn(String pharmacistMsisdn) {
        this.pharmacistMsisdn = pharmacistMsisdn;
    }

    public String getDistrictManagerEmail() {
        return districtManagerEmail;
    }

    public void setDistrictManagerEmail(String districtManagerEmail) {
        this.districtManagerEmail = districtManagerEmail;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public String getClinicPhoneNumber() {
        return clinicPhoneNumber;
    }

    public void setClinicPhoneNumber(String clinicPhoneNumber) {
        this.clinicPhoneNumber = clinicPhoneNumber;
    }

    public Integer getSafetyLevel() {
        return safetyLevel;
    }

    public void setSafetyLevel(Integer safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Boolean getActivated() {
        return activated;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		return true;
	}

    @Override
    public String toString() {
        return "User [id=" + id + ", msisdn=" + msisdn + ", encryptedPassword=" + encryptedPassword + ", salt=" + salt
                + ", clinicCode=" + clinicCode + ", clinicName=" + clinicName + ", coordinates=" + coordinates
                + ", pharmacistName=" + pharmacistName + ", pharmacistMsisdn=" + pharmacistMsisdn
                + ", clinicPhoneNumber=" + clinicPhoneNumber + ", districtManagerEmail=" + districtManagerEmail
                + ", safetyLevel=" + safetyLevel + ", leadTime=" + leadTime + ", activated=" + activated + "]";
    }
}
