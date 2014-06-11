package org.celllife.stock.domain.user;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -8978444547529630086L;

	private Long id;
	
	private String msisdn;
	
	private String encryptedPassword;

	private String password; // this is used for creation of users + password resets
	
	private String salt;
	
	private String clinicCode;
	
	private String clinicName;
	
	private String coordinates;

	private String clinicPhoneNumber;
   
	private String pharmacistName;

    private String pharmacistMsisdn;

    private String districtManagerEmail;

    private Integer safetyLevel;
    
    private Integer leadTime;
    
    private Boolean activated;
	
	public UserDto() {
		
	}
	
	public UserDto(User user) {
		super();
		this.id = user.getId();
		this.msisdn = user.getMsisdn();
		this.encryptedPassword = user.getEncryptedPassword();
		this.salt = user.getSalt();
		this.clinicCode = user.getClinicCode();
		this.clinicName = user.getClinicName();
		this.coordinates = user.getCoordinates();
		this.pharmacistName = user.getPharmacistName();
		this.clinicPhoneNumber = user.getClinicPhoneNumber();
		this.pharmacistMsisdn = user.getPharmacistMsisdn();
		this.districtManagerEmail = user.getDistrictManagerEmail();
		this.safetyLevel = user.getSafetyLevel();
		this.leadTime = user.getLeadTime();
		this.activated = user.isActivated();
	}

	public UserDto(String msisdn, String password, String clinicCode, String clinicName) {
		super();
		this.msisdn = msisdn;
		this.password = password;
		this.clinicCode = clinicCode;
		this.clinicName = clinicName;
	}

    public User toUser() {
        User newUser = new User(getMsisdn(), getEncryptedPassword(), getSalt(), getClinicCode(), getClinicName());
        newUser.setPharmacistName(getPharmacistName());
        newUser.setPharmacistMsisdn(getPharmacistMsisdn());
        newUser.setDistrictManagerEmail(getDistrictManagerEmail());
        newUser.setClinicPhoneNumber(getClinicPhoneNumber());
        newUser.setCoordinates(getCoordinates());
        newUser.setLeadTime(getLeadTime());
        newUser.setSafetyLevel(getSafetyLevel());
        newUser.setActivated(isActivated());
        return newUser;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

    public String getClinicPhoneNumber() {
        return clinicPhoneNumber;
    }

    public void setClinicPhoneNumber(String clinicPhoneNumber) {
        this.clinicPhoneNumber = clinicPhoneNumber;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
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
		UserDto other = (UserDto) obj;
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
        return "UserDto [id=" + id + ", msisdn=" + msisdn + ", encryptedPassword=" + encryptedPassword + ", password="
                + password + ", salt=" + salt + ", clinicCode=" + clinicCode + ", clinicName=" + clinicName
                + ", coordinates=" + coordinates + ", clinicPhoneNumber=" + clinicPhoneNumber + ", pharmacistName="
                + pharmacistName + ", pharmacistMsisdn=" + pharmacistMsisdn + ", districtManagerEmail="
                + districtManagerEmail + ", safetyLevel=" + safetyLevel + ", leadTime=" + leadTime + ", activated="
                + activated + "]";
    }

}
