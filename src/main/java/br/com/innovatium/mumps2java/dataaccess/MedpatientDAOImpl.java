package br.com.innovatium.mumps2java.dataaccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicType;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;

import br.com.innovatium.mumps2java.model.Medpatient;
import br.com.innovatium.mumps2java.model.MedpatientPK;

@Stateless
public class MedpatientDAOImpl implements MedpatientDAO {
	@PersistenceContext(name = "jpa-tutorial")
	private EntityManager em;
	
	@PersistenceContext(name = "relational")
	private EntityManager emrelational;

	public Medpatient find(String patientid) {
		return (Medpatient) em.find(Medpatient.class, new MedpatientPK(
				patientid));

	}

	public List<Medpatient> total(String patientid) {
		List l = null;
		for (int i = 0; i < 10; i++) {
			Date start = new Date();
			l = em.createQuery(
					"select p.name from Medpatient p where p.id.patientid like :patientid")
					.setParameter("patientid", patientid + "%").getResultList();
			System.out.println("Tempo de pesquisa de pacientes "
					+ (new Date().getTime() - start.getTime()) + "(ms)");

		}
		return l;

	}

	public void testarMedpentient(String id) {
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

		EntityManager em = emf.createEntityManager();
		for (int i = 0; i < 10; i++) {
			java.util.Date start = new java.util.Date();
			List l = em
					.createQuery(
							"select p.name from MEDPatientDynamic p where p.patientid like :patientid")
					.setParameter("patientid", id+ "%").getResultList();
			System.out.println("Tempo de pesquisa de pacientes "
					+ (new java.util.Date().getTime() - start.getTime())
					+ "(ms)");
		}

	}

	private EntityManagerFactory createEntityManagerFactory(
			DynamicClassLoader dcl, String persistenceUnit) {
		Map<Object, Object> properties = new HashMap<Object, Object>();
		//ExamplePropertiesLoader.loadProperties(properties);
		properties.put(PersistenceUnitProperties.CLASSLOADER, dcl);
		properties.put(PersistenceUnitProperties.WEAVING, "static");
		return Persistence.createEntityManagerFactory(persistenceUnit,
				properties);
	}

}
