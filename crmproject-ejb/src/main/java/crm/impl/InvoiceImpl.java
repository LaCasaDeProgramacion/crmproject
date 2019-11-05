package crm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.CommandProduct;
import crm.entities.Commande;
import crm.entities.Invoice;
import crm.entities.Product;
import crm.entities.User;
import crm.interfaces.IInvoiceServiceLocal;
import crm.interfaces.IInvoiceServiceRemote;
@Stateless
@LocalBean
public class InvoiceImpl implements IInvoiceServiceLocal,IInvoiceServiceRemote {
private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public void createInvoice(int cmd_id) {
		Query qcmdTest  = em.createQuery("SELECT cmd FROM Commande cmd WHERE cmd.command_id = :idus ");
		qcmdTest.setParameter("idus", cmd_id);
		Commande cmd = (Commande)qcmdTest.getSingleResult();
		int user_id = cmd.getIdus();
		
		User user = em.find(User.class,user_id);
		double prix = 0.0;
		Query qp  = em.createQuery("SELECT p FROM CommandProduct p WHERE p.cmd_id = :cmd_id ");
		qp.setParameter("cmd_id", cmd_id); 
		
		ArrayList<CommandProduct> lstComp = (ArrayList<CommandProduct>)qp.getResultList();
		
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for(CommandProduct cp : lstComp){
			prix += cp.getPrix();
			Query qProd  = em.createQuery("SELECT p FROM Product p WHERE p.id = :id_p ",Product.class);
			qProd.setParameter("id_p", cp.getProd_id());
			Product ppp =(Product)qProd.getSingleResult();
			ppp.setProductQuantity(ppp.getProductQuantity()-cp.getQt());
			em.merge(ppp);
		}
	  Invoice invoice = new Invoice();
	   invoice.setCmd(cmd);
	   invoice.setPrix(prix);
	   invoice.setUser(user);
	   em.merge(invoice);
	}

	@Override
	public void creatInvoice(int cmd_id, int id_user) {
			
	}
   
}
