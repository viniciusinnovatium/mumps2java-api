package br.com.innovatium.mumps2java.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the MEDPATIENT database table.
 * 
 */
@Embeddable
public class MedpatientPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String company = "0";

	private String patientid;

	public MedpatientPK() {
	}

	public MedpatientPK(String patientid) {
		this.patientid = patientid;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPatientid() {
		return this.patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MedpatientPK)) {
			return false;
		}
		MedpatientPK castOther = (MedpatientPK) other;
		return this.company.equals(castOther.company)
				&& this.patientid.equals(castOther.patientid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.company.hashCode();
		hash = hash * prime + this.patientid.hashCode();

		return hash;
	}
}