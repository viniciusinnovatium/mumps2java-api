package br.com.innovatium.mumps2java.dataaccess;

import javax.ejb.Local;

import br.com.innovatium.mumps2java.model.Medpatient;

@Local
public interface MedpatientDAO {
	Medpatient find(String patientid);

	void testarMedpatientEstatico(String patientid);

	void testarMedpatientDinamico(String patientid);

}
