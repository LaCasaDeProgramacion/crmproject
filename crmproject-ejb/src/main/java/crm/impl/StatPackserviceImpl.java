package crm.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.print.attribute.standard.MediaSize.Other;

import crm.entities.GainMoneyComparator;
import crm.entities.InvoicesPacks;
import crm.entities.Pack;
import crm.entities.StatPack;
import crm.entities.TitleStat;
import crm.interfaces.IStatPackServiceLocal;
import crm.interfaces.IStatPackServiceRemote;

@Stateless
@LocalBean
public class StatPackserviceImpl implements IStatPackServiceLocal, IStatPackServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	/******************************************************
	 * METHODS FOR SCHEDULE
	 ****************************************************************/
	@Override
	public void addstatpack(Pack pack) {
		System.out.println(
				"*****************************************Statpack ADDERR*********************************************");

		StatPack p = new StatPack();
		p.setPack(pack);
		p.setGainmoney(0.0);
		p.setQuantityselled(0);
		Calendar cal = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("MMMMM").format(cal.getTime()));
		p.setMonth(new SimpleDateFormat("MMMMM").format(cal.getTime()));
		p.setYear(new SimpleDateFormat("yyyy").format(cal.getTime()));
		p.setTitleStat(TitleStat.StarterPack);
		p.setBestPackforToday(false);
		p.setMostGainMoneyPackToday(false);
		p.setMostSelledQuantityPackToday(false);
		p.setNbrewinDaysinMonth(0);
		p.setCodeTitleAction("code0");
		p.setNumberoflosedays(0);
		em.persist(p);
	}

	@Override
	public void updatestatpack(Pack p) {
		System.out.println(
				"*****************************************Statpack New Pack Invoices*********************************************");

		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> query = em
				.createQuery("SELECT sp FROM StatPack sp  WHERE sp.pack=:pack AND sp.Month=:months AND sp.Year=:year ",
						StatPack.class)
				.setParameter("pack", p).setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> liststpack = query.getResultList();
		if (liststpack.isEmpty()) {
			System.out.println("Pack  stat not found let's create new one for this month and this year ");
			addstatpack(p);
		} else {
			StatPack sp = query.getSingleResult();
			TypedQuery<InvoicesPacks> queryinvoice = em
					.createQuery("SELECT p FROM InvoicesPacks p  WHERE p.pack=:pack AND statutstat=0 ",
							InvoicesPacks.class)
					.setParameter("pack", p);
			List<InvoicesPacks> listinvoicespacks = queryinvoice.getResultList();

			if (!listinvoicespacks.isEmpty()) {
				for (InvoicesPacks invoicespack : listinvoicespacks) {
					sp.setGainmoney(sp.getGainmoney()
							+ (invoicespack.getPack().getIntegratedprice() * invoicespack.getQuantityselled()));
					sp.setQuantityselled(sp.getQuantityselled() + invoicespack.getQuantityselled());
					em.merge(sp);
					invoicespack.setStatutstat(true);
					em.merge(invoicespack);
				}

			} else {
				System.out.println("no new invoices found!");
			}
		}

	}

	@Override
	public void BestpackforToday() {
		System.out.println(
				"*****************************************Assign Best pack for Today****************************************");
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE p.Month=:months AND p.Year=:year ", StatPack.class)

				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpack = queryone.getResultList();
		// StatPack mostgainMoneyPackToday = mostgainMoneyPackToday();
		for (StatPack stp : stpack) {
			if (stp.getMostGainMoneyPackToday()) {
				stp.setBestPackforToday(true);
				stp.setNbrewinDaysinMonth(stp.getNbrewinDaysinMonth() + 1);
				em.merge(stp);
			} else {
				stp.setBestPackforToday(false);
				em.merge(stp);
			}
		}

	}

	@Override
	public StatPack mostgainMoneyPackToday() {
		System.out.println(
				"*****************************************MostGainMoney*********************************************");
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year   ", StatPack.class)

				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpacks = queryone.getResultList();
		GainMoneyComparator comparebygainmoney = new GainMoneyComparator();

		Optional<StatPack> maxpackstatgainmoney = stpacks.stream().max((i, j) -> comparebygainmoney.compare(i, j));
		StatPack statpackmostgainedmoney = maxpackstatgainmoney.get();
		statpackmostgainedmoney.setMostGainMoneyPackToday(true);
		em.merge(statpackmostgainedmoney);

		for (StatPack p : stpacks) {
			if (!p.equals(statpackmostgainedmoney)) {
				p.setMostGainMoneyPackToday(false);
				em.merge(p);
			}
		}

		return statpackmostgainedmoney;

		// results

	}

	@Override
	public StatPack mostSelledQuantitypacktoday() {
		System.out.println(
				"*****************************************MostSelled Quantity*********************************************");

		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year   ", StatPack.class)

				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpacks = queryone.getResultList();

		Optional<StatPack> maxpackstatselledquantity = stpacks.stream().max((i, j) -> i.compareTo(j));
		StatPack statpackmostsellquantity = maxpackstatselledquantity.get();
		statpackmostsellquantity.setMostSelledQuantityPackToday(true);
		em.merge(statpackmostsellquantity);

		for (StatPack p : stpacks) {
			if (!p.equals(statpackmostsellquantity)) {
				p.setMostSelledQuantityPackToday(false);
				em.merge(p);
			}
		}
		return statpackmostsellquantity;
	}

	@Override
	public void updatestatpacktitle() {
		System.out.println(
				"*****************************************Titles upgrader*********************************************");

		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year   ", StatPack.class)

				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpacks = queryone.getResultList();

		// *********************************ASSOCIATE CODE TITLE
		// ACTIONS*********************************//
		// sorted list by quantity using stream() and compareto()
		List<StatPack> stpackssortedbyquantity = stpacks.stream().sorted((i, j) -> i.compareTo(j))
				.collect(Collectors.toList());
		// get list moyenne of Stats appel method :
		Map<String, List<StatPack>> map = new HashMap<String, List<StatPack>>();
		map = getmoyenneStatlist(stpackssortedbyquantity);
//		List<StatPack> positiveList = map.get("positiveList");
//		List<StatPack> negativelist = map.get("negativeList");
		List<StatPack> negativelisthalfnegative = map.get("negativeListHalfNegative");
		List<StatPack> negativelisthalfpositive = map.get("negativeListHalfPositive");
		List<StatPack> positivelisthalfnegative = map.get("positiveListHalfNegative");
		List<StatPack> positivelisthalfpositive = map.get("positiveListHalfPositive");

		for (StatPack sp : negativelisthalfnegative) {
			if (!sp.getTitleStat().equals(TitleStat.Stranger) && sp.getQuantityselled() != 0) {
				sp.setTitleStat(TitleStat.Stranger);
				
				em.merge(sp);
			}

		}

		for (StatPack sp : negativelisthalfpositive) {
			if (!sp.getTitleStat().equals(TitleStat.Disappointment)) {
				sp.setTitleStat(TitleStat.Disappointment);
				em.merge(sp);
			}
		}
		for (StatPack sp : positivelisthalfnegative) {
			if (!sp.getTitleStat().equals(TitleStat.Rising)) {

				sp.setTitleStat(TitleStat.Rising);
				em.merge(sp);
			}
		}
		for (StatPack sp : positivelisthalfpositive) {
			if (!sp.getTitleStat().equals(TitleStat.Trending)) {
				sp.setTitleStat(TitleStat.Trending);
				em.merge(sp);
			}
		}

	}
	@Override
	public void ArchivePackbyTitle() {
		TypedQuery<Pack> sp = em.createQuery("Select p.pack from StatPack p where p.numberoflosedays>=30 ",
				Pack.class);

		List<Pack> pack = sp.getResultList();
		
		
		
		if(!pack.isEmpty()) {
		for(Pack p :pack) {
			p.setArchivestatus(true);
			em.merge(p);
		}
		}else {
			System.out.println("List of pack to Archive is empty !!");
		}
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.numberoflosedays>=30", StatPack.class);

		List<StatPack> stp = queryone.getResultList();
			if(!stp.isEmpty())	{
				for(StatPack stpp :stp) {
					stpp.setNumberoflosedays(0);
					em.merge(stpp);
				}
			}else {
				System.out.println("List of StatPack to set 0 number of day fail is empty !!");
			}
	}
	
	@Override
	public void ArchiverCountDays() {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year   ", StatPack.class)

				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpacks = queryone.getResultList();
		if(!stpacks.isEmpty()) {
		for(StatPack sp:stpacks) {
			if(sp.getTitleStat().equals(TitleStat.Stranger)) {
				sp.setNumberoflosedays(sp.getNumberoflosedays()+1);
				em.merge(sp);
			}
		}
		}else {
			System.out.println("List of Stat pack of Stanger Empty !!");
		}
	}

	/******************************************************
	 * METHODS FOR JSON
	 ****************************************************************/

	@Override
	public StatPack jsonmostgainMoneyPackToday() {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em.createQuery(
				"SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year AND p.mostGainMoneyPackToday=1",
				StatPack.class).setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		StatPack stpacks = queryone.getSingleResult();

		return stpacks;
	}

	@Override
	public StatPack jsonSelledQuantitypacktoday() {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em.createQuery(
				"SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year AND p.mostSelledQuantityPackToday=1",
				StatPack.class).setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		StatPack stpacks = queryone.getSingleResult();

		return stpacks;
	}

	@Override
	public StatPack jsonBestpackforToday() {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery(
						"SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year AND p.BestPackforToday=1",
						StatPack.class)
				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		StatPack stpacks = queryone.getSingleResult();

		return stpacks;
	}

	@Override
	public StatPack jsonPackoftheMonth() {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year", StatPack.class)
				.setParameter("months", new SimpleDateFormat("MMMMM").format(cal.getTime()))
				.setParameter("year", new SimpleDateFormat("yyyy").format(cal.getTime()));
		List<StatPack> stpacks = queryone.getResultList();
		StatPack sp = new StatPack();
		Optional<StatPack> maxpackstatgainmoney = stpacks.stream().max((i, j) -> sp.compare(i, j));

		StatPack p = maxpackstatgainmoney.get();
		return p;

	}

	@Override
	public StatPack getStatpack(Pack packid) {

		TypedQuery<StatPack> query = em.createQuery("SELECT p FROM StatPack p WHERE p.pack=:pack", StatPack.class)
				.setParameter("pack", packid);
		StatPack sp = query.getSingleResult();
		return sp;

	}

	@Override
	public List<StatPack> getallStatPack() {
		TypedQuery<StatPack> query = em.createQuery("SELECT p FROM StatPack p", StatPack.class);
		List<StatPack> sp = query.getResultList();
		return sp;
	}

	@Override
	public List<StatPack> jsonStatPacksByMonth(String Month) {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months  ", StatPack.class)
				.setParameter("months", Month);
		List<StatPack> stpacks = queryone.getResultList();
		return stpacks;
	}

	@Override
	public List<StatPack> jsonStatPacksByMonthandYear(String Month, String Year) {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em
				.createQuery("SELECT p FROM StatPack p WHERE  p.Month=:months AND p.Year=:year ", StatPack.class)
				.setParameter("months", Month).setParameter("year", Year);
		List<StatPack> stpacks = queryone.getResultList();
		return stpacks;
	}

	@Override
	public List<StatPack> jsonStatPacksByYear(String Year) {
		Calendar cal = Calendar.getInstance();
		TypedQuery<StatPack> queryone = em.createQuery("SELECT p FROM StatPack p WHERE p.Year=:year ", StatPack.class)
				.setParameter("year", Year);
		List<StatPack> stpacks = queryone.getResultList();
		return stpacks;
	}

	@Override
	public List<StatPack> jsonStatPackByTitle(String ts) {

		TypedQuery<StatPack> queryone = em.createQuery("SELECT p FROM StatPack p WHERE   p.TitleStat=:tss",
				StatPack.class);

		if (ts.equalsIgnoreCase("Disappointment")) {
			queryone.setParameter("tss", TitleStat.Disappointment);
		} else if (ts.equals("Failer")) {
			queryone.setParameter("tss", TitleStat.Failer);
		} else if (ts.equals("Rising")) {
			queryone.setParameter("tss", TitleStat.Rising);
		} else if (ts.equals("StarterPack")) {
			queryone.setParameter("tss", TitleStat.StarterPack);
		} else if (ts.equals("Stranger")) {
			queryone.setParameter("tss", TitleStat.Stranger);
		} else if (ts.equals("Trending")) {
			queryone.setParameter("tss", TitleStat.Trending);
		}

		List<StatPack> stpacks = queryone.getResultList();
		return stpacks;
	}

	@Override
	public List<StatPack> jsonStatPackByEverething(String Month, String Year, String ts) {

		List<StatPack> statpackbytitle = jsonStatPackByTitle(ts);
		List<StatPack> statpackbymonth = jsonStatPacksByMonth(Month);
		List<StatPack> statpackbyyear = jsonStatPacksByYear(Year);
		List<StatPack> statpack = new ArrayList<StatPack>();
		for (StatPack stp : statpackbytitle) {
			if (statpackbymonth.contains(stp) && statpackbyyear.contains(stp)) {
				statpack.add(stp);
			}
		}

		List<StatPack> stpacks = statpack;
		return stpacks;
	}

	// **************************************METHOD
	// UTIL**********************************************************//

	static <T> List<Integer> indexOfAll(T obj, List<T> list) {
		final List<Integer> indexList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (obj.equals(list.get(i))) {
				indexList.add(i);
			}
		}
		return indexList;
	}

	public Map<String, List<StatPack>> getmoyenneStatlist(List<StatPack> sortedliststat) {
		Map<String, List<StatPack>> mapToReturn = new HashMap<String, List<StatPack>>();
		List<StatPack> negativelist = new ArrayList<StatPack>();
		List<StatPack> negativelisthalfnegative = new ArrayList<StatPack>();
		List<StatPack> negativelisthalfpositive = new ArrayList<StatPack>();
		List<StatPack> positivelist = new ArrayList<StatPack>();
		List<StatPack> positivelisthalfnegative = new ArrayList<StatPack>();
		List<StatPack> positivelisthalfpositive = new ArrayList<StatPack>();

		int sizeofsortedliststat = sortedliststat.size();

		if (TestPairOrImpaire(sizeofsortedliststat)) {
			for (int i = 0; i < sizeofsortedliststat / 2; i++) {
				negativelist.add(sortedliststat.get(i));
			}
			for (int i = sizeofsortedliststat / 2; i < sizeofsortedliststat; i++) {
				positivelist.add(sortedliststat.get(i));
			}
			int sizeofnegativelist = negativelist.size();
			int sizeofpositivelist = positivelist.size();
			if (TestPairOrImpaire(sizeofnegativelist)) {
				for (int i = 0; i < sizeofnegativelist / 2; i++) {
					negativelisthalfnegative.add(negativelist.get(i));
				}
				for (int i = sizeofnegativelist / 2; i < sizeofnegativelist; i++) {
					negativelisthalfpositive.add(negativelist.get(i));
				}
			} else {
				double d = (double) (sizeofnegativelist / 2);
				long halfofsizeofnegativelistrounded = Math.round(d);
				for (int i = 0; i < halfofsizeofnegativelistrounded; i++) {
					negativelisthalfnegative.add(negativelist.get(i));
				}
				for (int i = (int) (halfofsizeofnegativelistrounded); i < (halfofsizeofnegativelistrounded
						+ halfofsizeofnegativelistrounded) + 1; i++) {
					negativelisthalfpositive.add(negativelist.get(i));
				}

			}
			if (TestPairOrImpaire(sizeofpositivelist)) {
				for (int i = 0; i < sizeofpositivelist / 2; i++) {
					positivelisthalfnegative.add(positivelist.get(i));
				}
				for (int i = sizeofpositivelist / 2; i < sizeofpositivelist; i++) {
					positivelisthalfpositive.add(positivelist.get(i));
				}
			} else {
				double d = (double) (sizeofpositivelist / 2);
				long halfofsizeofpositivelistlistrounded = Math.round(d);
				for (int i = 0; i < halfofsizeofpositivelistlistrounded; i++) {
					positivelisthalfnegative.add(positivelist.get(i));
				}
				for (int i = (int) (halfofsizeofpositivelistlistrounded); i < (halfofsizeofpositivelistlistrounded
						+ halfofsizeofpositivelistlistrounded) + 1; i++) {
					positivelisthalfpositive.add(positivelist.get(i));
				}

			}
		} else {// if its impaire
			double dd = (double) (sizeofsortedliststat / 2);
			long halfofsizeofsortedlistrounded = Math.round(dd);

			for (int i = 0; i < halfofsizeofsortedlistrounded; i++) {
				negativelist.add(sortedliststat.get(i));
			}
			for (int i = (int) halfofsizeofsortedlistrounded; i < (halfofsizeofsortedlistrounded
					+ halfofsizeofsortedlistrounded) + 1; i++) {
				positivelist.add(sortedliststat.get(i));
			}
			int sizeofnegativelist = negativelist.size();
			int sizeofpositivelist = positivelist.size();
			if (TestPairOrImpaire(sizeofnegativelist)) {
				for (int i = 0; i < sizeofnegativelist / 2; i++) {
					negativelisthalfnegative.add(negativelist.get(i));
				}
				for (int i = sizeofnegativelist / 2; i < sizeofnegativelist; i++) {
					negativelisthalfpositive.add(negativelist.get(i));
				}
			} else {
				double d = (double) (sizeofnegativelist / 2);
				long halfofsizeofnegativelistrounded = Math.round(d);
				for (int i = 0; i < halfofsizeofnegativelistrounded; i++) {
					negativelisthalfnegative.add(negativelist.get(i));
				}
				for (int i = (int) (halfofsizeofnegativelistrounded); i < (halfofsizeofnegativelistrounded
						+ halfofsizeofnegativelistrounded) + 1; i++) {
					negativelisthalfpositive.add(negativelist.get(i));
				}

			}
			if (TestPairOrImpaire(sizeofpositivelist)) {
				for (int i = 0; i < sizeofpositivelist / 2; i++) {
					positivelisthalfnegative.add(positivelist.get(i));
				}
				for (int i = sizeofpositivelist / 2; i < sizeofpositivelist; i++) {
					positivelisthalfpositive.add(positivelist.get(i));
				}
			} else {
				double d = (double) (sizeofpositivelist / 2);
				long halfofsizeofpositivelistlistrounded = Math.round(d);
				for (int i = 0; i < halfofsizeofpositivelistlistrounded; i++) {
					positivelisthalfnegative.add(positivelist.get(i));
				}
				for (int i = (int) (halfofsizeofpositivelistlistrounded); i < (halfofsizeofpositivelistlistrounded
						+ halfofsizeofpositivelistlistrounded) + 1; i++) {
					positivelisthalfpositive.add(positivelist.get(i));
				}

			}

		}
		mapToReturn.put("positiveList", positivelist);
		mapToReturn.put("negativeList", negativelist);
		mapToReturn.put("positiveListHalfNegative", positivelisthalfnegative);
		mapToReturn.put("positiveListHalfPositive", positivelisthalfpositive);
		mapToReturn.put("negativeListHalfNegative", negativelisthalfnegative);
		mapToReturn.put("negativeListHalfPositive", negativelisthalfpositive);
		return mapToReturn;

	}

	public Boolean TestPairOrImpaire(int number) {
		int reste = number % 2;
		if (reste == 0) {
			return true;
		} else {
			return false;
		}
	}

	

	

}
