package crm.impl.prospecting;

import java.util.List;

import javax.ejb.*;
import javax.persistence.*;
import crm.entities.prospecting.Agent;
import crm.entities.prospecting.Location;
import crm.entities.prospecting.PointOfSale;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class AgentImpl implements IAgentLocal, IAgentRemote {
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<Agent> allAgents() {
		Query q = em.createQuery("SELECT a.id,  a.cin , a.number, a.firstName, a.lastName, a.role, a.email, a.dateBirth, a.accessPerm"
				+ ", a.drivenLicence FROM Agent a");
		return (List<Agent>) q.getResultList();
	}

	@Override
	public List<Agent> searchForAgent(int cin) {
		Query q = em.createQuery("SELECT a.id, a.cin , a.number, a.firstName, a.lastName,  a.email, a.dateBirth, a.accessPerm"
				+ ", a.drivenLicence FROM Agent a where a.cin = :cin");
		q.setParameter("cin", cin);
		return (List<Agent>) q.getResultList();
	}

	@Override
	public void addAgent(Agent a) {
		em.persist(a);
	}

	@Override
	public boolean  deleteAgent(int id) {
			Agent a = em.find(Agent.class, id); 
			if (a!=null)
			{
				em.remove(a);
				//Query q = em.createQuery("DELETE FROM Agent a WHERE a.id = :id");
		       // q.setParameter("id", id);
		       // q.executeUpdate();
		        return true ; 
				
			}
			
			return false ; 
		
	}

	@Override
	public int updateAgent(Agent agent) {
		Agent a = em.find(Agent.class, agent.getId());
		if (a!= null)
		{
			a.setCin(agent.getCin());
			a.setNumber(agent.getNumber());
			a.setFirstName(agent.getFirstName());
			a.setLastName(agent.getLastName());
			a.setEmail(agent.getEmail());
			a.setDateBirth(agent.getDateBirth());
			a.setRole(agent.getRole());
			a.setAccessPerm(agent.isAccessPerm());
			a.setDrivenLicence(agent.isDrivenLicence());
			return em.merge(a).getId(); 
			 
			
		}
		 return 0; 
	
	}

}
