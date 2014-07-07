package br.com.innovatium.mumps2java.dataaccess;

import javax.ejb.Local;

import br.com.innovatium.mumps2java.model.Medpatient;

@Local
public interface MedpatientDAO {
	Medpatient find(String patientid);

	void testarPesquisa(String patientid);
	
	void testarPesquisaMedpatientEstatico(String patientid);

	void testarPesquisaMedpatientDinamico(String patientid);

	void testarInclusaoMedpatientEstatico();
}
