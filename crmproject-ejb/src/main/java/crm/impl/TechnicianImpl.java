package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.Technician;
import crm.interfaces.ITechnicianLocal;
import crm.interfaces.ITechnicianRemote;

@Stateless
@LocalBean
public class TechnicianImpl implements ITechnicianLocal,ITechnicianRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTechnician(Technician technician) {
		em.persist(technician);
		
	}

	@Override
	public void UpdateTechnician(Technician technician) {
		Technician technicianBD=em.find(Technician.class, technician.getId());
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
		Technician tech=em.find(Technician.class, idtechnician);
		TypedQuery<Technician> q = em.createQuery("SELECT t.technician FROM Complaints t WHERE t.technician = :technician", Technician.class);
		q.setParameter("technician", tech);
		Technician technician=q.getSingleResult();
		if(technician!=null)
		{
			return false;
		}
		return true;
	}
}
