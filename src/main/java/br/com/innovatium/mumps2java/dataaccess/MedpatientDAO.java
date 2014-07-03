package br.com.innovatium.mumps2java.dataaccess;

import java.util.List;

import javax.ejb.Local;

import br.com.innovatium.mumps2java.model.Medpatient;

@Local
public interface MedpatientDAO {
	Medpatient find(String patientid);

	List<Medpatient> total(String patientid);

	void testarMedpentient(String patientid);

}
