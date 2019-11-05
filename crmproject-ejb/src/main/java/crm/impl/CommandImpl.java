package crm.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import crm.entities.Basket;
import crm.entities.BasketProduct;
import crm.entities.CommandProduct;
import crm.entities.Commande;
import crm.entities.EtatCommand;
import crm.entities.Product;
import crm.entities.Roles;
import crm.entities.Store;
import crm.entities.User;
import crm.interfaces.IBasketServiceLocale;
import crm.interfaces.IBasketServiceRemote;
import crm.interfaces.ICommandServiceLocal;
import crm.interfaces.ICommandServiceRemote;
@Stateless
@LocalBean

public class CommandImpl implements ICommandServiceRemote,ICommandServiceLocal {
private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	@Override
	public Boolean createCommand(int product_id, int user_id) {
		
		Product pp = em.find(Product.class,product_id);
		
		User user = em.find(User.class,user_id);
		int id = user.getId();
		Query qp  = em.createQuery("SELECT p FROM CommandProduct p WHERE p.prod_id = :idp And p.user_id = :user_id ");
		qp.setParameter("idp", product_id); 
		qp.setParameter("user_id", user_id);
		
		if(!qp.getResultList().isEmpty()) {
			return false;
		}
		else {
			Query qcmdTest  = em.createQuery("SELECT cmd FROM Commande cmd WHERE cmd.idus = :idus ");
			qcmdTest.setParameter("idus", id);
			  if(qcmdTest.getResultList().isEmpty()) {
				  Commande cmd = new Commande();
					cmd.setIdus(user_id);
					cmd.setEtat(EtatCommand.Commande);
					cmd.setDureLimite(3);
					
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					cmd.setDateCommand(timestamp);
					Roles etat = user.getRole();
					
					
						if(etat.equals(Roles.PROSPECT)) {
							user.setRole(Roles.CLIENT);
						}
						
						cmd.setUser(user);
					
						CommandProduct cp = new CommandProduct();
						
						cp.setProd_id(product_id);
						cp.setCmd_id(cmd.getCommand_id());
						cp.setUser_id(user_id);
						cp.setPrix(pp.getProductPrice());
						cp.setQt(pp.getProductQuantity());
						
						em.merge(cmd);
						em.merge(cp);
						em.merge(user);
						return true;
			  }else {
		
				ArrayList<Commande> lst =   (ArrayList<Commande>) qcmdTest.getResultList();
				for(Commande cmd : lst) {
					
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
					Timestamp dateCmd = cmd.getDateCommand();
					
					Date now = new Date(timestamp.getTime());
					Date dateProd = new Date(dateCmd.getTime());
					
					Date d3 = new Date(now.getTime() - dateProd.getTime());
					long nbjour = d3.getTime()/1000/60/60;
				   
					
					if(nbjour<24) {
						
						
						
							CommandProduct cp = new CommandProduct();
							cp.setCmd_id(cmd.getCommand_id());
							cp.setProd_id(product_id);
							cp.setUser_id(user_id);
							cp.setPrix(pp.getProductPrice());
							cp.setQt(pp.getProductQuantity());
							em.merge(cp);
							return true;
						
					
						
					}
					else {
						  Commande cm = new Commande();
							
							cm.setEtat(EtatCommand.Commande);
							cm.setDureLimite(3);
							Timestamp timestam = new Timestamp(System.currentTimeMillis());
							cmd.setIdus(user_id);
							
							
							cm.setDateCommand(timestam);
							
							
							Roles etat = user.getRole();
							
							
								if(etat.equals(Roles.PROSPECT)) {
									user.setRole(Roles.CLIENT);
								}
								
								cm.setUser(user);
								CommandProduct cp = new CommandProduct();
								cp.setUser_id(user_id);
								cp.setProd_id(product_id);
								cp.setCmd_id(cm.getCommand_id());
								cp.setPrix(pp.getProductPrice());
								cp.setQt(pp.getProductQuantity());
								em.merge(cmd);
								em.merge(cp);
								em.merge(user);
								return true;
					}
				}
				  return false;

			  }
			 
		}
      
	}
	@Override//unitil
	public Boolean createCmd(int product_id, int user_id) {
		
		return false;
	}
	@Override//fini
	public Boolean addProductToCommand(int product_id, int user_id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override//   unitil
	public Boolean removeProductFromCommand(int product_id, int user_id) {
	    Product pp = em.find(Product.class,product_id);
		
		User user = em.find(User.class,user_id);
		int id = user.getId();
		Query qp   = em.createQuery("DELETE p FROM CommandProduct p WHERE p.prod_id = :idp");
		qp.setParameter("idp", product_id); 
		
		qp.executeUpdate();
		


		return null;
	}
	@Override
	public Boolean AnnulCommand(int command_id) {
		Commande cmd = em.find(Commande.class,command_id);
		cmd.setEtat(EtatCommand.Commande);
		em.merge(cmd);
		Query qp  = em.createQuery("DELETE FROM CommandProduct p WHERE p.cmd_id =:idp ");
		qp.setParameter("idp", command_id); 
		int index = qp.executeUpdate();
		
		return null;
	}
	@Override
	public List<Commande> allCommandByUser(int user_id) {
		Query qp  = em.createQuery("SELECT c FROM Commande c WHERE c.idus =:idp ");
		qp.setParameter("idp", user_id);
		ArrayList<Commande> lstCmd = (ArrayList<Commande>)qp.getResultList();
		for(Commande c : lstCmd) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Timestamp dateCmd = c.getDateCommand();
			
			Date now = new Date(timestamp.getTime());
			Date dateProd = new Date(dateCmd.getTime());
			
			Date d3 = new Date(now.getTime() - dateProd.getTime());
			long nbjour = d3.getTime()/1000/60/60;
			
			if(nbjour>72) {
				Query qsupCmd  = em.createQuery("DELETE  FROM Commande c WHERE c.command_id =:idp ");
				qp.setParameter("idp", c.getCommand_id());
			}
		}
		Query qp1  = em.createQuery("SELECT c FROM Commande c WHERE c.idus =:idp ");
		qp1.setParameter("idp", user_id);
		ArrayList<Commande> lstCmdsup = (ArrayList<Commande>)qp.getResultList();
			return  lstCmdsup;
		
	}
	
	@Override
	public List<Commande> CommandByUser(int user_id) {
		
        Query qcmdTest  = em.createQuery("SELECT cmd FROM Commande cmd WHERE cmd.idus = :idus ");
		qcmdTest.setParameter("idus", user_id);
		
		return (List<Commande>)qcmdTest.getResultList();
	}
	@Override
	public int removeProductFromComd(int product_id, int user_id) {
        User user = em.find(User.class,user_id);
		int id = user.getId();
		Query qp   = em.createQuery("DELETE FROM CommandProduct p WHERE p.prod_id = :idp And p.user_id = :user_id ");
		qp.setParameter("idp", product_id); 
		qp.setParameter("user_id",user_id);
		int index = qp.executeUpdate();
		return index;
	}
	@Override
	public int AnnulCmd(int command_id) {
		Commande cmd = em.find(Commande.class,command_id);
		cmd.setEtat(EtatCommand.Annuler);
		Query qpp  = em.createQuery("DELETE FROM Commande c WHERE c.command_id =:idp ");
		qpp.setParameter("idp", command_id); 
		Query qp  = em.createQuery("DELETE FROM CommandProduct p WHERE p.cmd_id =:idp ");
		qp.setParameter("idp", command_id); 
		int index = qp.executeUpdate();
		return index;
	}
	
}


