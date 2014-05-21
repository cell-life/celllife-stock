package org.celllife.stock.domain.user;

import java.io.Serializable;

public class ClinicDto implements Serializable {

	private static final long serialVersionUID = -8978444547529630086L;

	private String msisdn;
		
	private String clinicCode;
	
	private String clinicName;

	private String coordinates;
	
	public ClinicDto() {
		
	}
	
	public ClinicDto(User user) {
		super();
		this.msisdn = user.getMsisdn();
		this.clinicCode = user.getClinicCode();
		this.clinicName = user.getClinicName();
		this.coordinates = user.getCoordinates();
	}

	public ClinicDto(String msisdn, String clinicCode, String clinicName, String coordinates) {
		super();
		this.msisdn = msisdn;
		this.clinicCode = clinicCode;
		this.clinicName = clinicName;
		this.coordinates = coordinates;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinicCode == null) ? 0 : clinicCode.hashCode());
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
		ClinicDto other = (ClinicDto) obj;
		if (clinicCode == null) {
			if (other.clinicCode != null)
				return false;
		} else if (!clinicCode.equals(other.clinicCode))
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
		return "ClinicDto [msisdn=" + msisdn + ", clinicCode=" + clinicCode + ", clinicName=" + clinicName
				+ ", coordinates=" + coordinates + "]";
	}

}
