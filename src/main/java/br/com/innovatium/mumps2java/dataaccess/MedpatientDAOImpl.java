package br.com.innovatium.mumps2java.dataaccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicType;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;

import br.com.innovatium.mumps2java.model.Medpatient;
import br.com.innovatium.mumps2java.model.MedpatientPK;

@Stateless
public class MedpatientDAOImpl implements MedpatientDAO {
	@PersistenceContext(name = "relational")
	private EntityManager em;

	public MedpatientDAOImpl() {
		criarMedpatientDynamicType();
	}

	public Medpatient find(String patientid) {
		return (Medpatient) em.find(Medpatient.class, new MedpatientPK(
				patientid));

	}

	public void testarMedpatientEstatico(String patientid) {
		System.out.println("Gerando medpatient estatico ----");
		DescriptiveStatistics stats = new DescriptiveStatistics();
		long timeexpended = 0;
		for (int i = 0; i < 10; i++) {
			Date start = new Date();
			em.createQuery(
					"select p.name from Medpatient p where p.id.patientid like :patientid")
					.setParameter("patientid", patientid + "%").getResultList();
			timeexpended = new Date().getTime() - start.getTime();
			stats.addValue(timeexpended);
		}

		System.out.println("Media: " + stats.getMean());
		System.out.println("Desvio Padrao: " + stats.getStandardDeviation());

	}

	public void testarMedpatientDinamico(String id) {

		System.out.println("Gerando medpatient dinamico ----");
		DescriptiveStatistics stats = new DescriptiveStatistics();
		long timeexpended = 0;
		for (int i = 0; i < 10; i++) {
			java.util.Date start = new java.util.Date();
			em.createQuery(
					"select p.name from MEDPatientDynamic p where p.patientid like :patientid")
					.setParameter("patientid", id + "%").getResultList();
			timeexpended = new java.util.Date().getTime() - start.getTime();
			stats.addValue(timeexpended);
		}

		System.out.println("Media: " + stats.getMean());
		System.out.println("Desvio Padrao: " + stats.getStandardDeviation());
	}

	private void criarMedpatientDynamicType() {
		// Create a dynamic class loader and create the types.
		DynamicClassLoader dcl = new DynamicClassLoader(Thread.currentThread()
				.getContextClassLoader());
		Class<?> pacienteClass = dcl
				.createDynamicClass("br.com.innovatium.netmanager.MEDPatientDynamic");

		JPADynamicTypeBuilder pacienteBuilder = new JPADynamicTypeBuilder(
				pacienteClass, null, "MEDPatient");
		pacienteBuilder.setPrimaryKeyFields("company", "patientid");
		pacienteBuilder.addDirectMapping("patientid", String.class,
				"MEDPatient.patientid");
		pacienteBuilder.addDirectMapping("company", String.class,
				"MEDPatient.company");
		pacienteBuilder.addDirectMapping("name", String.class,
				"MEDPatient.name");
		DynamicType pacienteType = pacienteBuilder.getType();

		// Create an entity manager factory.
		EntityManagerFactory emf = createEntityManagerFactory(dcl, "relational");

		// Create JPA Dynamic Helper (with the emf above) and after the types
		// have been created and add the types through the helper.
		JPADynamicHelper helper = new JPADynamicHelper(emf);
		helper.addTypes(true, true, pacienteType);

	}

	private EntityManagerFactory createEntityManagerFactory(
			DynamicClassLoader dcl, String persistenceUnit) {
		Map<Object, Object> properties = new HashMap<Object, Object>();
		// ExamplePropertiesLoader.loadProperties(properties);
		properties.put(PersistenceUnitProperties.CLASSLOADER, dcl);
		properties.put(PersistenceUnitProperties.WEAVING, "static");
		return Persistence.createEntityManagerFactory(persistenceUnit,
				properties);
	}

}
