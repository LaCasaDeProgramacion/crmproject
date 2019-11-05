package crm.impl;

import java.util.List;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.Product;
import crm.entities.Technician;
import crm.interfaces.ITechnicianLocal;
import crm.interfaces.ITechnicianRemote;

@Stateless
@LocalBean
public class TechnicianImpl implements ITechnicianLocal, ITechnicianRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTechnician(Technician technician) {
		em.persist(technician);

	}

	@Override
	public void UpdateTechnician(Technician technician) {
		Technician technicianBD = em.find(Technician.class, technician.getId());
		technicianBD.setTechnicianFirstName(technician.getTechnicianFirstName());
		technicianBD.setTechnicianPhoneNumber(technician.getTechnicianPhoneNumber());
		technicianBD.setTechnicianSecondName(technician.getTechnicianSecondName());
		technicianBD.setTechnicianSpecialty(technician.getTechnicianSpecialty());
		em.merge(technicianBD);

	}

	@Override
	public List<Technician> getAllTechnician() {
		TypedQuery<Technician> q = em.createQuery("SELECT t FROM Technician t", Technician.class);
		return (List<Technician>) q.getResultList();
	}

	@Override
	public void DeleteTechnician(int idtechnician) {
		em.remove(em.find(Technician.class, idtechnician));

	}

	@Override
	public boolean IsAvailable(int idtechnician) {
		Technician tech = em.find(Technician.class, idtechnician);
		TypedQuery<Complaints> q = em.createQuery("SELECT t FROM Complaints t WHERE t.technician = :technician",
				Complaints.class);
		q.setParameter("technician", tech);
		Complaints c = q.getSingleResult();
		if (c.getTechnician() != null) {
			return false;
		}
		return true;
	}

	@Override
	public Technician getrandomtechnician() {
		Random r = new Random();
		Query q = em.createQuery("SELECT t FROM Technician t");
		return (Technician) q.getResultList().get(r.nextInt(3));

	}
}
