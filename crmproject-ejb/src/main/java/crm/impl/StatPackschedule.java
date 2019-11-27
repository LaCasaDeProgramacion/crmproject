package crm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Pack;
import crm.entities.StatPack;

@Stateless
public class StatPackschedule {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

@EJB 
StatPackserviceImpl statpackserviceimpl;
@Schedule(minute="*/1",hour="*" ,persistent=false)
public void ScheduleMethods() {
	System.out.println("*************************Add New Packs Stats**************************************");
	TypedQuery<Pack> packids = em.createQuery("SELECT p FROM Pack p",Pack.class);
	List<Pack> packs = packids.getResultList();
	for(Pack pack : packs) {
	System.out.println(pack.getTitle());
	}
	TypedQuery<Pack> statpackids = em.createQuery("SELECT p.pack FROM StatPack p",Pack.class);
	List<Pack> sppacklist = statpackids.getResultList();
	
	List<Pack> packtoadd =new ArrayList<Pack>();
	for (Pack pk : packs) {
		if(sppacklist.contains(pk)) {
			System.out.println("pack already have Stat :"+pk);
		}else
		{
			packtoadd.add(pk);
		}
	}
	for(Pack p :packtoadd) {
	statpackserviceimpl.addstatpack(p);
	System.out.println("Stat created For Pack"+p.getTitle());
	}

	System.out.println("*************************Update Packs Stats*******************************************************");
	TypedQuery<Pack> packidss = em.createQuery("SELECT p FROM Pack p",Pack.class);
	List<Pack> packss = packidss.getResultList();
	for (Pack pk : packss) {
		statpackserviceimpl.updatestatpack(pk);
	}
	System.out.println("*************************Update mostgainMoneyPackToday*******************************************");
	statpackserviceimpl.mostgainMoneyPackToday();
	System.out.println("*************************Update mostSelledQuantitypacktoday**************************************");
	statpackserviceimpl.mostSelledQuantitypacktoday();
	System.out.println("*************************Update BestpackforToday*************************************************");
	statpackserviceimpl.BestpackforToday();
	System.out.println("*************************Update updatestatpacktitle*************************************************");
	statpackserviceimpl.updatestatpacktitle();
	System.out.println("*************************Archiver Count Days *************************************************");
	statpackserviceimpl.ArchiverCountDays();
	System.out.println("*************************Archive pack Action if Stat 30 time in NegativelistHalfNegative *************************************************");
	statpackserviceimpl.ArchivePackbyTitle();
}
	
	
}
