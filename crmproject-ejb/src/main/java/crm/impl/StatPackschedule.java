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
<<<<<<< HEAD

//@EJB
//StatPackserviceImpl statpackserviceimpl;
//@Schedule(second="*/5",minute="*" ,hour="*")
	/*
public void everyHourCreateStattest() { //if new pack created we create stat
=======
@EJB
StatPackserviceImpl statpackserviceimpl;
//@Schedule(second="*",minute="*" ,hour="*/1")
//@EJB
//StatPackserviceImpl statpackserviceimpl;
//@Schedule(second="*/5",minute="*" ,hour="*")
/*public void everyHourCreateStattest() { //if new pack created we create stat
>>>>>>> 1b5f99f8956888a67178bd452ac57beb54bc7be1

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
	
<<<<<<< HEAD
}
*/
//@Schedule(second="*",minute="*" ,hour="*/1")
//public void everyDayupdatestatstest() {//if we found new facture of buying packs we update stat
=======
}*/
//@Schedule(second="*",minute="*" ,hour="*/1")

public void everyDayupdatestatstest() {//if we found new facture of buying packs we update stat

/*public void everyDayupdatestatstest() {//if we found new facture of buying packs we update stat
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
>>>>>>> 1b5f99f8956888a67178bd452ac57beb54bc7be1
	
	
	/*statpackserviceimpl.updatestatpack(packid, gainmoney, quantityselled, changetitle);*/
	
	
	
//}
/*@Schedule(second="",minute="" ,hour="")
public void everyhourtestexistingpackstat() {
	
}  */


<<<<<<< HEAD
//}

=======
}
}
>>>>>>> 1b5f99f8956888a67178bd452ac57beb54bc7be1
