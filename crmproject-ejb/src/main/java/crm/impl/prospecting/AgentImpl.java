package crm.impl.prospecting;

import java.util.List;

import javax.ejb.*;
import javax.persistence.*;

import crm.entities.Roles;
import crm.entities.prospecting.Agent;
import crm.entities.prospecting.Location;
import crm.entities.prospecting.PointOfSale;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class AgentImpl implements IAgentLocal, IAgentRemote {
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<Agent> allAgents() {
		TypedQuery<Agent> q = em.createQuery("SELECT c FROM Agent c", Agent.class);
		return (List<Agent>) q.getResultList();
	}

	@Override
	public List<Agent> searchForAgent(int cin) {
		Query q = em.createQuery("SELECT a FROM Agent a where a.cin = :cin");
		q.setParameter("cin", cin);
		return (List<Agent>) q.getResultList();
	}

	@Override
	public boolean addAgent(Agent a) {
		if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
		{
			em.persist(a);
			return true ; 
		}
		else return false ; 
	}

	@Override
	public int  deleteAgent(int id) {
			Agent a = em.find(Agent.class, id); 
			if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
			{
			if (a!=null)
			{
				em.remove(a);
		        return 1 ; 
			}
			return -1 ; 
			}return 0; 
	}

	@Override
	public int updateAgent(Agent agent) {
		Agent a = em.find(Agent.class, agent.getId());
		if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
		{
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
			return 1 ; 
			
			
		}
		else return -1; 
		}
		 return 0; 
	
	}

}
