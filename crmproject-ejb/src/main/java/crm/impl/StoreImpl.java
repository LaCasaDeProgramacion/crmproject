package crm.impl;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		
		 /*
		 java.util.Date date= new Date();
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 int month = cal.get(Calendar.MONTH);
		
		 //////////////////////////////////////
		 

		
		 String str = new String("2014-09-01 08:00:00.000");
		 String timestartwinter = str.split("\\s")[1].split("\\.")[0];
		 System.out.print(timestartwinter);
		 
		 String str2 = new String("2014-09-01 18:00:00.000");
		 String timeendwinter = str2.split("\\s")[1].split("\\.")[0];
		 System.out.print(timeendwinter);
		 
		 ///////////////////////Summer
		 String str3 = new String("2014-09-01 08:00:00.000");
		 String timestartsummer = str3.split("\\s")[1].split("\\.")[0];
		 System.out.print(timestartsummer);
		 
		 String str4 = new String("2014-09-01 14:00:00.000");
		 String timeendsummer = str4.split("\\s")[1].split("\\.")[0];
		 System.out.print(timeendsummer);
		 	
		 
		 if(month==10 || month==12 || month==11 || month==12 || month==13 ) {
			store.setStart(timestartwinter); 
			store.setEnd(timeendwinter);
		 }else {
			 store.setStart(timestartsummer); 
				store.setEnd(timeendsummer); 
		 }
		 */
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
	
	
	/* worked
	@Override
	public double distance(double lat1, double lat2, double lon1, double lon2) {
		 // The math module contains a function 
        // named toRadians which converts from 
        // degrees to radians. 
        lon1 = Math.toRadians(lon1); 
        lon2 = Math.toRadians(lon2); 
        lat1 = Math.toRadians(lat1); 
        lat2 = Math.toRadians(lat2); 
  
        // Haversine formula  
        double dlon = lon2 - lon1;  
        double dlat = lat2 - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
                 + Math.cos(lat1) * Math.cos(lat2) 
                 * Math.pow(Math.sin(dlon / 2),2); 
              
        double c = 2 * Math.asin(Math.sqrt(a)); 
  
        // Radius of earth in kilometers. Use 3956  
        // for miles 
        double r = 6371; 
  
        // calculate the result 
        return(c * r); 
	}
	*/
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
  
        // Haversine formula  
        double dlon = lonstore - lon1;  
        double dlat = latstore - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
                 + Math.cos(lat1) * Math.cos(latstore) 
                 * Math.pow(Math.sin(dlon / 2),2); 
              
        double c = 2 * Math.asin(Math.sqrt(a)); 
  
        // Radius of earth in kilometers. Use 3956  
        // for miles 
        double r = 6371; 
  
        // calculate the result 
        return(c * r); 
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
		
		
	
  long end = store.getEnd().getTime();
  long start = store.getStart().getTime();
  int diff = ((int) (end - start)/1000)/60;
  //test
  diff= diff/60;
  return "Le store"+" "+store.getStore_name()+" "+ "se ferme  aprés"+" "+diff+ " "+" heures";
  
  
  
		
		
	}
	
	}
	
		
	


