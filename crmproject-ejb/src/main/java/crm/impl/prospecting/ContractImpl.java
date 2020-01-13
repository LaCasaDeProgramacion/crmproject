package crm.impl.prospecting;


import java.sql.Date;
import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ContractImpl implements IContractLocal, IContractRemote  {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<Contract> allContracts() {
		TypedQuery<Contract> q = em.createQuery("SELECT c FROM Contract c", Contract.class);
		return (List<Contract>) q.getResultList();
	}

	@Override
	public List<Contract> searchForContract(String title) {
		Query q = em.createQuery("SELECT c FROM Contract c where c.title = :title");
		q.setParameter("title", title);
		return (List<Contract>) q.getResultList(); 
	}
	@Override 
	public Contract getById(int id)
	{
		return em.find(Contract.class,id); 
	}

	@Override
	public Contract addContract(String title , Date startDate, Date endDate, float salary, String comment,String status, int idAgent ) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
		//{
			Contract contract = new Contract(); 
			Agent agent = em.find(Agent.class,idAgent); 
			//if ((agent != null)&& (agent.getContract()==null) )
			//{
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
				return contract ; //added
			//}
			//else return -1; 
			
		//}return 0; //user 
		
		
	
		
		
	}

	@Override
	public int updateContract(int id,String title, Date startDate, Date endDate, float salary, String comment,String status, int idAgent) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
		//{
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
				return 1 ; //updated
	
			}
			else return -1; //contract - agent 
		//}return 0; //user 
	}

	@Override
	public int deleteContract(int id) {
		Contract c = em.find(Contract.class, id); 
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole()== Roles.VENDOR)
		//{
			if (c!=null)
			{
				Agent a = c.getAgent(); 
				a.setContract(null);
				em.merge(a); 
				
				c.setAgent(null);
				em.merge(c); 
				em.remove(c);
		        return 1 ; //deleted
			}
			else return -1; //contract 
		//} return 1; //user
	}

	
}
