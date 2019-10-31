package crm.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.Pack;
import crm.entities.StatPack;
import crm.entities.TitleStat;
import crm.interfaces.IStatPackServiceLocal;
import crm.interfaces.IStatPackServiceRemote;

@Stateless
@LocalBean
public class StatPackserviceImpl implements IStatPackServiceLocal,IStatPackServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void addstatpack(Pack pack) {
          StatPack p = new StatPack();
          p.setPack(pack);
		  p.setGainmoney(0.0);
		  p.setQuantityselled(0);
		  Calendar cal = Calendar.getInstance();
		  System.out.println(new SimpleDateFormat("MMMMM").format(cal.getTime()));
		  p.setMonth(new SimpleDateFormat("MMMMM").format(cal.getTime()));
		  p.setYear(new SimpleDateFormat("yyyy").format(cal.getTime()));
		  p.setTitleStat(TitleStat.StartingFromTheBottom);
		  em.persist(p);
	}

	@Override
	public void updatestatpack(Pack packid,double gainmoney ,int quantityselled,boolean changetitle) {
		Pack p = em.find(Pack.class, packid);
		TypedQuery<StatPack> query = em.createQuery("SELECT sp FROM StatPack sp  WHERE sp.pack=:pack  ",StatPack.class)
				.setParameter("pack", p);
		StatPack sp = query.getSingleResult();
		sp.setGainmoney(sp.getGainmoney()+gainmoney);
		sp.setQuantityselled(sp.getQuantityselled()+quantityselled);
		Calendar cal = Calendar.getInstance();
		sp.setMonth(new SimpleDateFormat("MMMMM").format(cal.getTime()));
		sp.setYear(new SimpleDateFormat("yyyy").format(cal.getTime()));
		
		//test if we want to change title
		if(changetitle==true) {
			//a faire
		}else {
			// a faire
		}
		em.merge(sp);
		
	}

	

	@Override
	public StatPack getStatpack(Pack packid) {
		Pack p = em.find(Pack.class, packid);
		TypedQuery<StatPack> query = em.createQuery("SELECT p FROM StatPack p WHERE p.pack=:pack",StatPack.class)
				.setParameter("pack", p);
		StatPack sp = query.getSingleResult();
		return sp;
		
	}

	@Override
	public List<StatPack> getallStatPack() {
		TypedQuery<StatPack> query = em.createQuery("SELECT p FROM StatPack p",StatPack.class);
		List<StatPack> sp = query.getResultList();
		return sp;
	}
}
