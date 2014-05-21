package org.celllife.stock.domain.alert;

import java.io.Serializable;

import org.celllife.stock.domain.user.ClinicDto;
import org.celllife.stock.domain.user.User;

public class AlertSummaryDto implements Serializable {

	private static final long serialVersionUID = 7075188646105441127L;
	
	private ClinicDto clinic;
	
	private Long level1AlertCount;
	private Long level2AlertCount;
	private Long level3AlertCount;
	
	public AlertSummaryDto() {
	}

	public AlertSummaryDto(User user, Long level1Count, Long level2Count, Long level3Count) {
		super();
		this.clinic = new ClinicDto(user);
		this.level1AlertCount = level1Count;
		this.level2AlertCount = level2Count;
		this.level3AlertCount = level3Count;
	}
	
	public AlertSummaryDto(ClinicDto clinic, Long level1Count, Long level2Count, Long level3Count) {
		super();
		this.clinic = clinic;
		this.level1AlertCount = level1Count;
		this.level2AlertCount = level2Count;
		this.level3AlertCount = level3Count;
	}
	
	public AlertSummaryDto(String msisdn, String clinicCode, String clinicName, String coordinates, Long level1Count, Long level2Count, Long level3Count) {
		super();
		this.clinic = new ClinicDto(msisdn, clinicCode, clinicName, coordinates);
		this.level1AlertCount = level1Count;
		this.level2AlertCount = level2Count;
		this.level3AlertCount = level3Count;
	}

	public ClinicDto getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDto clinic) {
		this.clinic = clinic;
	}

	public Long getLevel1AlertCount() {
		return level1AlertCount;
	}

	public void setLevel1AlertCount(Long level1AlertCount) {
		this.level1AlertCount = level1AlertCount;
	}

	public Long getLevel2AlertCount() {
		return level2AlertCount;
	}

	public void setLevel2AlertCount(Long level2AlertCount) {
		this.level2AlertCount = level2AlertCount;
	}

	public Long getLevel3AlertCount() {
		return level3AlertCount;
	}

	public void setLevel3AlertCount(Long level3AlertCount) {
		this.level3AlertCount = level3AlertCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinic == null) ? 0 : clinic.hashCode());
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
		AlertSummaryDto other = (AlertSummaryDto) obj;
		if (clinic == null) {
			if (other.clinic != null)
				return false;
		} else if (!clinic.equals(other.clinic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlertSummaryDto [clinic=" + clinic + ", level1AlertCount=" + level1AlertCount + ", level2AlertCount="
				+ level2AlertCount + ", level3AlertCount=" + level3AlertCount + "]";
	}
}
