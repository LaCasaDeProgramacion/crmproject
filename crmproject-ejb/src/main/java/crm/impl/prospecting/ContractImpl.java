package crm.impl.prospecting;


import java.sql.Date;
import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class ContractImpl implements IContractLocal, IContractRemote  {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<Contract> allContracts() {
		Query q = em.createQuery(
				"SELECT c.id,  c.title, c.startDate, c.endDate,c.status, c.salary, c.comment, a.firstName , a.lastName FROM Contract c JOIN c.agent a");
		return (List<Contract>) q.getResultList();
	}

	@Override
	public List<Contract> searchForContract(String title) {
		Query q = em.createQuery("SELECT c.id, c.title, c.startDate, c.endDate,c.status, c.salary, c.comment, a.firstName , a.lastName FROM Contract c JOIN c.agent a"
				+ " where c.title = :title");
		q.setParameter("title", title);
		return (List<Contract>) q.getResultList(); 
	}

	@Override
	public boolean addContract(String title , Date startDate, Date endDate, float salary, String comment,String status, int idAgent ) {
		Contract contract = new Contract(); 
		Agent agent = em.find(Agent.class,idAgent); 
		if ((agent != null)&& (agent.getContract()==null) )
		{
			contract.setAgent(agent);
			agent.setContract(contract);
			
			contract.setComment(comment);
			contract.setEndDate(endDate);
			contract.setStartDate(startDate);
			contract.setSalary(salary);
			contract.setTitle(title);
			contract.setStatus(status);
			
			em.persist(contract);
			em.merge(agent); 
			return true ; 
		}
		return false ; 
		
	}

	@Override
	public boolean updateContract(int id,String title, Date startDate, Date endDate, float salary, String comment,String status, int idAgent) {
		Contract contract = em.find(Contract.class, id);
		Agent agent = em.find(Agent.class, idAgent); 
		if (contract!= null && agent!=null)
		{
			contract.setComment(comment);
			contract.setEndDate(endDate);
			contract.setStartDate(startDate);
			contract.setSalary(salary);
			contract.setTitle(title);
			contract.setStatus(status);
			
			if (contract.getAgent().getId() == idAgent)
			{
				contract.setAgent(em.find(Agent.class, idAgent));
				System.out.println("le meme agent");
			}
			else {
				
				Agent agent2 = contract.getAgent(); 
				agent2.setContract(null);
				em.merge(agent2); 
				
				agent.setContract(contract);
				em.merge(agent); 
		
				contract.setAgent(agent);
			}
			em.merge(contract); 
			return true ; 

		}
		 return false ; 
	}

	@Override
	public boolean deleteContract(int id) {
		Contract c = em.find(Contract.class, id); 
		if (c!=null)
		{
			Agent a = c.getAgent(); 
			a.setContract(null);
			em.merge(a); 
			
			c.setAgent(null);
			em.merge(c); 
			em.remove(c);
	        return true ; 
		}
		
		return false ; 
	}

	
}
