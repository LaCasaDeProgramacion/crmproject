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
import crm.entities.ProductsPackPk;
import crm.entities.Promotion;
import crm.entities.prospecting.Vehicule;
import crm.interfaces.IPackServiceRemote;

@Stateless
@LocalBean

public class PackserviceImpl implements IPackServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	List<Pack> packss;
	@EJB
	PromotionserviceImpl promservice;

	@Override
	public void addpack(Pack pack) {
		System.out.println("IN : Add Pack");

		Date d = new Date();
		Timestamp createdate = new Timestamp(d.getTime());
		pack.setCreatedate(createdate);

		pack.setArchivestatus(false);

		em.persist(pack);

		System.out.println("OUT : Add Pack");
	}

	@Override
	public void assignproducttopack(List<Integer> Products, int packid) {
		double i = 0;
		List<Integer> items = new ArrayList<>();
		Pack pack = em.find(Pack.class, packid);
		for (int idproduct : Products) {
			ProductsPackPk prodpackpk = new ProductsPackPk(packid, idproduct);
			ProductsPack pp = new ProductsPack();

			Product prod = em.find(Product.class, idproduct);
			pp.setProductspackPK(prodpackpk);
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
		Query query = em
				.createQuery("SELECT p FROM Pack p JOIN ProductsPack pp ON p.id = pp.pack JOIN Product prod ON prod.id = pp.product "
						+ " WHERE p.title LIKE :code or p.description LIKE :code or prod.productName LIKE :code or prod.productDescription LIKE :code "
						+ "  GROUP BY pp.product ORDER BY p.createdate DESC");
		query.setParameter("code", "%" + Packtext + "%");
		List<Object> results = query.getResultList();
		
		return results;
	}

	@Override
	public List<Product> findproductsPack(int packid) {

		Pack pack = em.find(Pack.class, packid);

		Query query = em.createQuery("SELECT pp.product FROM ProductsPack pp WHERE pp.pack =:pack ").setParameter("pack",
				pack);
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
	public void removeProductfrompack(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pack updatepack(Pack pack, int idpack, List<Integer> Products) {

		List<Product> newproductspack = new ArrayList<Product>();// toutes les produit
		List<Product> oldproductspack;// les produits déja assigned to pack
        List<Product> addproducts=new ArrayList<Product>();
		List<Product> toodeletefrompack = new ArrayList<Product>();// les produit to delete

		// simple attribute to update
		Pack packtoupdate = em.find(Pack.class, idpack);
		packtoupdate.setTitle(pack.getTitle());
		packtoupdate.setDescription(pack.getDescription());
		packtoupdate.setPicture(pack.getPicture());
		packtoupdate.setValidfrom(pack.getValidfrom());
		packtoupdate.setValiduntil(pack.getValiduntil());

		if (!Products.isEmpty()) {

			
			oldproductspack = findproductsPack(idpack);
			// add product to list of the new productsPack we want to add to pack
			for (int idprod : Products) {
				Product allproduct = em.find(Product.class, idprod);
				System.out.println("productts name : " + allproduct.getProductName());
				newproductspack.add(allproduct);

			}
			// testing of duplicate and unique product between product we want to add
			// already added to pack
			for (Product product : newproductspack) {
				if (oldproductspack.contains(product)) {
					toodeletefrompack.add(product);
				}else {
					addproducts.add(product);
				}
			}
			// delete duplicated list of product
			if (!toodeletefrompack.isEmpty()) {
                     for(Product prod:toodeletefrompack) {
				Query query = em.createQuery("DELETE FROM ProductsPack pp WHERE pp.product=:product AND pp.pack =:pack")
						.setParameter("product", prod).setParameter("pack",
						packtoupdate);
				query.executeUpdate();
                     }
			}
			double i = 0;
			List<Integer> items = new ArrayList<>();
			for (Product prod : newproductspack) {
				System.out.println("productss to add iddddddd :   " + prod.getId());
				ProductsPackPk prodpackpk = new ProductsPackPk(idpack, prod.getId());
				ProductsPack pp = new ProductsPack();
				//Product prod = em.find(Product.class, prod.getId());
				pp.setProductspackPK(prodpackpk);
				pp.setPack(packtoupdate);
				pp.setProduct(prod); // integrated price
				double integratedprice = promservice.productPromotionValue(prod.getId());
				pp.setProductIntegratedPrice(integratedprice); // integrated quantity
				pp.setIntegratedQuantity(prod.getProductQuantity()); // to set integrated price with default value it
																		// can be modified
				i = (i + integratedprice);

				// to set pack integrated quantity with default value
				items.add(prod.getProductQuantity());
				
				if (oldproductspack.contains(prod)) {
					em.merge(pp);
				}else {
					em.persist(pp);
				}

			}
			// add the new list product
			///////////////////////////////////////////////////////////////

			// set pack integratedprice integratedquantity
			if (pack.getIntegratedprice() == 99999999.99) {
				packtoupdate.setIntegratedprice(i);

			} else {
				packtoupdate.setIntegratedprice(pack.getIntegratedprice());
			}
			if (pack.getIntegratedquantity() == 99999999) {
				int minimumquantity = findMin(items);
				packtoupdate.setIntegratedquantity(minimumquantity);
			} else {
				packtoupdate.setIntegratedquantity(pack.getIntegratedquantity());
			}

		} else if (Products.isEmpty()) {

			packtoupdate.setProductsPack(null);
			if (pack.getIntegratedprice() == 99999999.99) {
				packtoupdate.setIntegratedprice(0.0);

			} else {
				packtoupdate.setIntegratedprice(pack.getIntegratedprice());
			}
			if (pack.getIntegratedquantity() == 99999999) {
				packtoupdate.setIntegratedquantity(0);

			} else {
				packtoupdate.setIntegratedquantity(pack.getIntegratedquantity());
			}

		}

		em.persist(packtoupdate);
		return packtoupdate;

	}

	@Override
	public Pack findpackbyid(int id) {
		Pack p = em.find(Pack.class, id);
		return p;
	}

	@Override
	public List<Pack> availablepacks() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.createdate DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> notavailablepacks() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE DATE(:timestamp) NOT BETWEEN e.validfrom AND e.validuntil OR e.archivestatus=1 ORDER BY e.createdate DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> packsNotArchived() {
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE  e.archivestatus=0 ORDER BY e.createdate DESC",
				Pack.class);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> packsArchived() {
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE  e.archivestatus=1 ORDER BY e.createdate DESC",
				Pack.class);
		List<Pack> results = query.getResultList();
		return results;
	}

	@Override
	public List<Pack> availablepacksorderbypriceDESC() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.integratedprice DESC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		
		List<Pack> results = query.getResultList();
		return results;
		
	}

	@Override
	public List<Pack> availablepacksorderbypriceASC() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Pack> query = em.createQuery("select e from Pack e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil AND e.archivestatus=0 ORDER BY e.integratedprice ASC",
				Pack.class);
		query.setParameter("timestamp", timestamp);
		
		List<Pack> results = query.getResultList();
		return results;
	}

	


}
