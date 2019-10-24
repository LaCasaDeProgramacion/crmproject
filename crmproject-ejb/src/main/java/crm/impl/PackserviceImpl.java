package crm.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sound.midi.Soundbank;

import crm.entities.Pack;
import crm.entities.Product;
import crm.interfaces.IPackServiceRemote;

@Stateless
@LocalBean
public class PackserviceImpl implements IPackServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	Set<Product> products;
	Set<Pack> packss;
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
	public Pack updatepack(Pack pack) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void removePack(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductfrompack(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pack> searchPack(String Packtext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findproductsPack(Pack pack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disableepackforclient(Pack pack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double calculatepackprice(Pack pack) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Set<Product> assignproducttopack(List<Integer> Products, int packid) {
		
		Pack pack = em.find(Pack.class, packid);
		for(int idproduct:Products) {
			Product prod = em.find(Product.class, idproduct);
			products = pack.getProducts();
			packss = prod.getPack();
			products.add(prod);
			packss.add(pack);
			prod.setPack(packss);
		}
		pack.setProducts(products);
		
		return products;
                 
		
	}


	@Override
	public void archivepack(int idpack) {
		System.out.println("IN : archivepack");
	  Pack p = em.find(Pack.class, idpack);
	  p.setArchivestatus(true);
	  System.out.println("OUT: archivepack");
		
	}

	


	
	

}
