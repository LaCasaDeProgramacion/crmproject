package crm.impl.prospecting;

import java.util.Date;
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
				"SELECT c.title, c.startDate, c.endDate,c.status, c.salary, c.comment, a.firstName , a.lastName FROM Contract c JOIN c.agent a");
		return (List<Contract>) q.getResultList();
	}

	@Override
	public List<Contract> searchForContract(String title) {
		Query q = em.createQuery("SELECT c.title, c.startDate, c.endDate,c.status, c.salary, c.comment, a.firstName , a.lastName FROM Contract c JOIN c.agent a"
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
		if (contract!= null)
		{
			contract.setComment(comment);
			contract.setEndDate(endDate);
			contract.setStartDate(startDate);
			contract.setSalary(salary);
			contract.setTitle(title);
			contract.setStatus(status);
			
			//chercher si quelqu'un a ce contrat 
			Query q = em.createQuery("Select a.id, c.id from Agent a join a.contract c"); 
			Object o = (Object) q.getSingleResult(); 
			if (o==null)
			{
				Agent agent = em.find(Agent.class,idAgent); 
				if (agent != null)
				{
					contract.setAgent(agent);
					agent.setContract(contract);
					em.merge(agent); 
				}
				
			}
			/*
			Agent agent = em.find(Agent.class,idAgent); 
			if ((agent != null) && (agent.getContract()==null))
			{
				contract.setAgent(agent);
				agent.setContract(contract);
				em.merge(agent); 
			}*/
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
			Query q = em.createQuery("DELETE FROM Contract a WHERE a.id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	        return true ; 
		}
		
		return false ; 
	}

	
}
