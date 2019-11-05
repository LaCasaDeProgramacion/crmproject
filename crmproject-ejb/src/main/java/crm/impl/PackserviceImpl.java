package crm.impl;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sound.midi.Soundbank;

import crm.entities.Pack;
import crm.entities.Product;
import crm.entities.ProductsPack;

import crm.entities.Promotion;
import crm.entities.Roles;
import crm.entities.prospecting.Vehicule;
import crm.interfaces.IPackServiceRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean

public class PackserviceImpl implements IPackServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	List<Pack> packss;
	@EJB
	PromotionserviceImpl promservice;

	@Override
	public Boolean addpack(Pack pack) {
		if(UserSession.getInstance().getRole()==Roles.ADMIN || UserSession.getInstance().getRole()==Roles.VENDOR) {
		System.out.println("IN : Add Pack");

		Date d = new Date();
		Timestamp createdate = new Timestamp(d.getTime());
		pack.setCreatedate(createdate);

		pack.setArchivestatus(false);

		em.persist(pack);

		System.out.println("OUT : Add Pack");
		return true;
		}else {
			return false ;
		}
	}

	@Override
	public Boolean assignproducttopack(List<Integer> Products, int packid) {
		if(UserSession.getInstance().getRole()==Roles.ADMIN || UserSession.getInstance().getRole()==Roles.VENDOR) {
		double i = 0;
		List<Integer> items = new ArrayList<>();
		Pack pack = em.find(Pack.class, packid);
		for (int idproduct : Products) {
			ProductsPack pp = new ProductsPack();

			Product prod = em.find(Product.class, idproduct);

			pp.setPack(pack);
			pp.setProduct(prod);
			// integrated price
			double integratedprice = promservice.productPromotionValue(idproduct);
			pp.setProductIntegratedPrice(integratedprice);
			// integrated quantity
			pp.setIntegratedQuantity(prod.getProductQuantity());
			// to set integrated price with default value it can be modified
			i = (i + integratedprice);

			// to set pack integrated quantity with default value
			items.add(prod.getProductQuantity());

			em.persist(pp);

		}
		// set pack integratedprice integratedquantity

		pack.setIntegratedprice(i);
		int minimumquantity = findMin(items);
		pack.setIntegratedquantity(minimumquantity);
		em.persist(pack);
		return true;
		}else {
			return false ;
		}

	}

	@Override
	public void archivepack(int idpack) {
		System.out.println("IN : archivepack");
		Pack p = em.find(Pack.class, idpack);
		p.setArchivestatus(true);
		System.out.println("OUT: archivepack");

	}

	@Override
	public void unarchivingpack(int idpack) {
		System.out.println("IN : unarchivingpack");
		Pack p = em.find(Pack.class, idpack);
		p.setArchivestatus(false);
		System.out.println("OUT: unarchivingpack");

	}

	@Override
	public void removePack(int id) {

		Pack p = em.find(Pack.class, id);

		Query queryone = em.createQuery("DELETE FROM ProductsPack pp WHERE pp.pack=:pack");
		queryone.setParameter("pack", p);
		queryone.executeUpdate();
		Query querythree = em.createQuery("DELETE FROM StatPack pp WHERE pp.pack=:pack");
		querythree.setParameter("pack", p);
		querythree.executeUpdate();
		Query querytwo = em.createQuery("DELETE FROM Pack p WHERE p.id=:id");
		querytwo.setParameter("id", id);
		querytwo.executeUpdate();

	}

	@Override
	public List<Object> searchPack(String Packtext) {
		Query query = em.createQuery(
				"SELECT p FROM Pack p JOIN ProductsPack pp ON p.id = pp.pack JOIN Product prod ON prod.id = pp.product "
						+ " WHERE p.title LIKE :code or p.description LIKE :code or prod.productName LIKE :code or prod.productDescription LIKE :code "
						+ "  GROUP BY pp.product ORDER BY p.createdate DESC");
		query.setParameter("code", "%" + Packtext + "%");
		List<Object> results = query.getResultList();
		if (results.isEmpty()) {
			Query querytwo = em.createQuery(
					"SELECT prod FROM Product prod JOIN ProductsPack pp ON p.id = pp.pack JOIN Pack p ON prod.id = pp.product "
							+ " WHERE p.title LIKE :code or p.description LIKE :code or prod.productName LIKE :code or prod.productDescription LIKE :code "
							+ "  GROUP BY pp.product ORDER BY p.createdate DESC");
			query.setParameter("code", "%" + Packtext + "%");
			List<Object> prodResult = query.getResultList();
			return prodResult;
		} else {
			return results;
		}

	}

	@Override
	public List<Product> findproductsPack(int packid) {

		Pack pack = em.find(Pack.class, packid);

		Query query = em.createQuery("SELECT pp.product FROM ProductsPack pp WHERE pp.pack =:pack ")
				.setParameter("pack", pack);
		List<Product> products = query.getResultList();

		return products;

	}

	public static Integer findMin(List<Integer> list) {

		// check list is empty or not
		if (list == null || list.size() == 0) {
			return Integer.MAX_VALUE;
		}

		// create a new list to avoid modification
		// in the original list
		List<Integer> sortedlist = new ArrayList<>(list);

		// sort list in natural order
		Collections.sort(sortedlist);

		// first element in the sorted list
		// would be minimum
		return sortedlist.get(0);
	}

	@Override
	public Pack updatepack(Pack pack, int idpack, List<Integer> Products) {
		if (em.find(Pack.class, idpack) != null) {
			Pack packtoupdate = em.find(Pack.class, idpack);
			// simple attribut
			packtoupdate.setTitle(pack.getTitle());
			packtoupdate.setDescription(pack.getDescription());
			packtoupdate.setPicture(pack.getPicture());
			packtoupdate.setValidfrom(pack.getValidfrom());
			packtoupdate.setValiduntil(pack.getValiduntil());
			if (!Products.isEmpty()) {
				List<Product> oldproductspack = findproductsPack(idpack);// nzedha test notfound mbe3ed
				List<Product> newproductpack = new ArrayList<Product>();
				List<Product> toDeletefromProductPack = new ArrayList<Product>();
				for (int id : Products) {
					if (em.find(Product.class, id) != null) {
						newproductpack.add(em.find(Product.class, id));
					} else {
						System.out.println("Product not found in data base");
					}
				}

				for (Product p : newproductpack) {
					if (oldproductspack.contains(p)) {
						toDeletefromProductPack.add(p);
					}
				}

				for (Product p : oldproductspack) {
					Query query = em
							.createQuery("DELETE FROM ProductsPack pp WHERE pp.product=:product AND pp.pack =:pack")
							.setParameter("product", p).setParameter("pack", packtoupdate);
					query.executeUpdate();

				}

				// inserer nouvelle list
				double i = 0;
				List<Integer> items = new ArrayList<>();
				Pack packretaked = em.find(Pack.class, idpack);
				for (Product p : newproductpack) {

					ProductsPack pp = new ProductsPack();
					pp.setPack(packtoupdate);
					pp.setProduct(p);
					// integrated price
					double integratedprice = promservice.productPromotionValue(p.getId());
					pp.setProductIntegratedPrice(integratedprice);
					// integrated quantity
					pp.setIntegratedQuantity(p.getProductQuantity());
					// to set integrated price with default value it can be modified
					i = (i + integratedprice);

					// to set pack integrated quantity with default value
					items.add(p.getProductQuantity());

					em.persist(pp);

				}
				// set pack integratedprice integratedquantity

				packtoupdate.setIntegratedprice(i);
				int minimumquantity = findMin(items);
				packtoupdate.setIntegratedquantity(minimumquantity);
				em.merge(packtoupdate);

			} else {
				System.out.println("nouvelle list product is empty");
			}
			return packtoupdate;
		} else {
			return null;
		}

	}

	@Override
	public Pack findpackbyid(int id) {
		Pack p = em.find(Pack.class, id);
		return p;
	}

	@Override
	public List<Pack> availablepacks() {
		ZoneId z = ZoneId.of(TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138));
		ZonedDateTime now = ZonedDateTime.now(z);
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery(
				"select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.createdate DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> notavailablepacks() {
		ZoneId z = ZoneId.of(TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138));
		ZonedDateTime now = ZonedDateTime.now(z);
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery(
				"select e from Pack e WHERE DATE(:timestamp) NOT BETWEEN e.validfrom AND e.validuntil OR e.archivestatus=1 ORDER BY e.createdate DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> packsNotArchived() {
		TypedQuery<Pack> query = em
				.createQuery("select e from Pack e WHERE  e.archivestatus=0 ORDER BY e.createdate DESC", Pack.class);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> packsArchived() {
		TypedQuery<Pack> query = em
				.createQuery("select e from Pack e WHERE  e.archivestatus=1 ORDER BY e.createdate DESC", Pack.class);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> availablepacksorderbypriceDESC() {
		ZoneId z = ZoneId.of(TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138));
		ZonedDateTime now = ZonedDateTime.now(z);
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery(
				"select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.integratedprice DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);

		List<Pack> results = query.getResultList();
		return results;

	}

	@Override
	public List<Pack> availablepacksorderbypriceASC() {
		ZoneId z = ZoneId.of(TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138));
		ZonedDateTime now = ZonedDateTime.now(z);
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery(
				"select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.integratedprice ASC",
				Pack.class);
		query.setParameter("timestamp", timestamp);

		List<Pack> results = query.getResultList();
		return results;
	}

}
