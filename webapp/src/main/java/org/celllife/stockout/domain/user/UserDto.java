package org.celllife.stockout.domain.user;

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
	}

	public UserDto(String msisdn, String password, String clinicCode, String clinicName) {
		super();
		this.msisdn = msisdn;
		this.password = password;
		this.clinicCode = clinicCode;
		this.clinicName = clinicName;
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
		return "User [id=" + id + ", msisdn=" + msisdn + ", encryptedPassword=" + encryptedPassword + ", salt=" + salt
				+ ", clinicCode=" + clinicCode + ", clinicName=" + clinicName + "]";
	}
}
