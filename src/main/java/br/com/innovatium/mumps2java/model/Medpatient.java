package br.com.innovatium.mumps2java.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MEDPATIENT database table.
 * 
 */
@Entity
@NamedQuery(name="Medpatient.findAll", query="SELECT m FROM Medpatient m")
public class Medpatient implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MedpatientPK id;

	@Temporal(TemporalType.DATE)
	private Date admissiondate;

	private Timestamp admitted;

	@Lob
	private String allergies;

	private String bed;

	private String changedby;

	private Timestamp changeddate;

	private String city;

	private String createdby;

	private Timestamp createddate;

	private String currentlyadmitted;

	private String department;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String email;

	private String externalcode;

	private String free1;

	private String free10;

	private String free11;

	private String free12;

	private String free13;

	private String free14;

	private String free15;

	private String free2;

	private String free3;

	private String free4;

	private String free5;

	private String free6;

	private String free7;

	private String free8;

	private String free9;

	private String gender;


	@Column(name="ID_SEQ")
	private BigDecimal idSeq;

	private String location;

	@Temporal(TemporalType.DATE)
	private Date mothersdob;

	private String mothersname;

	private String name;

	private String othernames;

	private String pid;

	private String room;

	private String room1;

	private String ssn;

	@Column(name="STATE_")
	private String state;

	private String street;

	private String surname;

	private String tel;

	private String zipcode;

	public Medpatient() {

	}

	public MedpatientPK getId() {
		return this.id;
	}

	public void setId(MedpatientPK id) {
		this.id = id;
	}

	public Date getAdmissiondate() {
		return this.admissiondate;
	}

	public void setAdmissiondate(Date admissiondate) {
		this.admissiondate = admissiondate;
	}

	public Timestamp getAdmitted() {
		return this.admitted;
	}

	public void setAdmitted(Timestamp admitted) {
		this.admitted = admitted;
	}

	public String getAllergies() {
		return this.allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getBed() {
		return this.bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getChangedby() {
		return this.changedby;
	}

	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}

	public Timestamp getChangeddate() {
		return this.changeddate;
	}

	public void setChangeddate(Timestamp changeddate) {
		this.changeddate = changeddate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public String getCurrentlyadmitted() {
		return this.currentlyadmitted;
	}

	public void setCurrentlyadmitted(String currentlyadmitted) {
		this.currentlyadmitted = currentlyadmitted;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExternalcode() {
		return this.externalcode;
	}

	public void setExternalcode(String externalcode) {
		this.externalcode = externalcode;
	}

	public String getFree1() {
		return this.free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	public String getFree10() {
		return this.free10;
	}

	public void setFree10(String free10) {
		this.free10 = free10;
	}

	public String getFree11() {
		return this.free11;
	}

	public void setFree11(String free11) {
		this.free11 = free11;
	}

	public String getFree12() {
		return this.free12;
	}

	public void setFree12(String free12) {
		this.free12 = free12;
	}

	public String getFree13() {
		return this.free13;
	}

	public void setFree13(String free13) {
		this.free13 = free13;
	}

	public String getFree14() {
		return this.free14;
	}

	public void setFree14(String free14) {
		this.free14 = free14;
	}

	public String getFree15() {
		return this.free15;
	}

	public void setFree15(String free15) {
		this.free15 = free15;
	}

	public String getFree2() {
		return this.free2;
	}

	public void setFree2(String free2) {
		this.free2 = free2;
	}

	public String getFree3() {
		return this.free3;
	}

	public void setFree3(String free3) {
		this.free3 = free3;
	}

	public String getFree4() {
		return this.free4;
	}

	public void setFree4(String free4) {
		this.free4 = free4;
	}

	public String getFree5() {
		return this.free5;
	}

	public void setFree5(String free5) {
		this.free5 = free5;
	}

	public String getFree6() {
		return this.free6;
	}

	public void setFree6(String free6) {
		this.free6 = free6;
	}

	public String getFree7() {
		return this.free7;
	}

	public void setFree7(String free7) {
		this.free7 = free7;
	}

	public String getFree8() {
		return this.free8;
	}

	public void setFree8(String free8) {
		this.free8 = free8;
	}

	public String getFree9() {
		return this.free9;
	}

	public void setFree9(String free9) {
		this.free9 = free9;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getIdSeq() {
		return this.idSeq;
	}

	public void setIdSeq(BigDecimal idSeq) {
		this.idSeq = idSeq;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getMothersdob() {
		return this.mothersdob;
	}

	public void setMothersdob(Date mothersdob) {
		this.mothersdob = mothersdob;
	}

	public String getMothersname() {
		return this.mothersname;
	}

	public void setMothersname(String mothersname) {
		this.mothersname = mothersname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOthernames() {
		return this.othernames;
	}

	public void setOthernames(String othernames) {
		this.othernames = othernames;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoom1() {
		return this.room1;
	}

	public void setRoom1(String room1) {
		this.room1 = room1;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}