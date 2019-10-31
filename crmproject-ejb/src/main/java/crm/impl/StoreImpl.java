package crm.impl;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Category;
import crm.entities.Product;
import crm.entities.Store;
import crm.interfaces.IStoreServiceLocal;
import crm.interfaces.IStoreServiceRemote;
import io.ipinfo.api.IPInfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;


@Stateless
@LocalBean
public class StoreImpl implements IStoreServiceRemote, IStoreServiceLocal{
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	@Override
	public void addStore(String store_name) {
		 Store store = new Store();
		 
	
		 IPInfo ipInfo = IPInfo.builder().setToken("930323b76e6a48").setCountryFile(new File("path/to/file.json")).build();
		 store.setStore_name(store_name);
		
		
		 try {
	            IPResponse response = ipInfo.lookupIP("197.20.88.115");

	            
	           store.setStore_latitude(response.getLatitude());
	            store.setStore_longitude(response.getLongitude());
store.setStore_city(response.getCity());
	        } catch (RateLimitedException ex) {
	            System.out.println(ex);
	        }
		 em.persist(store);
	    }
	
	
	
	//recuperer l'atitude et longitude d l'utilisateur via addresse IP et comparer avec celle de 
	//store qui va etre saisi dans postman, dans la phase angular ça va etre automatique grace à l'api googlemaps
	//la methode POST dans la phase angular qui va se consister à selectionner le marker(store) point depuis google maps 
	@Override
	public double distance(double latstore,double lonstore) {
		
		 IPInfo ipInfo = IPInfo.builder().setToken("930323b76e6a48").setCountryFile(new File("path/to/file.json")).build();

	        try {
	        	 IPResponse response = ipInfo.lookupIP("196.229.204.155");

	            // Print out the Latitude and Longitude
	            System.out.println(response.getLatitude());
	            System.out.println(response.getLongitude());
	            String testlat = response.getLatitude();

	            double valuelat = Double.valueOf(testlat);
	        String testlon= response.getLongitude();
	        double valuelon = Double.valueOf(testlon);		 
		 //hardcoding les coordinates de user
		double  lat1=0;
	double 	 lon1=0; 
        lon1 = Math.toRadians(valuelon); 
        lonstore = Math.toRadians(lonstore); 
        lat1 = Math.toRadians(valuelat); 
        latstore = Math.toRadians(latstore); 
  
        // formule Haversine   
        double dlon = lonstore - lon1;  
        double dlat = latstore - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
                 + Math.cos(lat1) * Math.cos(latstore) 
                 * Math.pow(Math.sin(dlon / 2),2); 
              
        double c = 2 * Math.asin(Math.sqrt(a)); 
  
        // Radius de la terre  en  kilometres. utiliser 3956  
        // en miles 
        double r = 6371; 
 
        // Calcul du resultat
        return (c * r); 
	        } catch (RateLimitedException ex) {
	        	System.out.println(ex);
	        }
			return latstore;			
	}
	@Override
	public Store liststorebydate(int store_id) {
		if(em.contains(em.find(Store.class, store_id))==true) {
			Store store = em.find(Store.class, store_id);
		return store;	
		}else
		return null;
	}


	@Override
	public Store findbyId(int store_id) {
	
			Query q = em.createQuery("SELECT s FROM Store s where s.store_id = :store_id");
			q.setParameter("store_id", store_id);
		return (Store) q.getSingleResult();
	}


	@Override
	public String datestore(int store_id) {
		
		
		
		Store store = findbyId(store_id);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
  long end = store.getEnd().getTime();
  
  long start = store.getStart().getTime();
  int diff = ((int) (end - start)/1000)/60;
  //test
  diff= diff/60;
  return "Le store"+" "+store.getStore_name()+" "+ "se ferme  aprés"+" "+diff+ " "+" heures";
  
  
  
		
		
	}



	@Override
	public void deleteStore(int store_id) {
		Query q = em.createQuery("DELETE FROM Store s WHERE s.store_id = :store_id");
        q.setParameter("store_id", store_id);
        q.executeUpdate();
		
	}



	@Override
	public List<Store> searchForstore(String store_name) {
		Query q = em.createQuery("SELECT s FROM Store s where s.store_name = :store_name");
		q.setParameter("store_name", store_name);
		return (List<Store>) q.getResultList();
	}



	@Override
	public int updateStore(int store_id, Timestamp end, Timestamp start, String store_city, String store_name) {
		Store s = em.find(Store.class, store_id);
		if(s!=null)
		{
			 s.setEnd(end);
		       s.setStart(start);
		       s.setStore_city(store_city);
		       s.setStore_name(store_name);
		       
		        em.merge(s);
		        return 1;
		}
		return 0;
			
	}



	@Override
	public List<Store> allStores() {
		Query q = em.createQuery("SELECT s FROM Product p where p.productStatus = :productStatus");
		
		return (List<Store>) q.getResultList();
		
	}



	


	
	
	}
	
		
	


