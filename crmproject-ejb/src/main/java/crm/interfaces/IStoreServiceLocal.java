package crm.interfaces;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import crm.entities.Product;
import crm.entities.Store;
import io.ipinfo.api.errors.RateLimitedException;

public interface IStoreServiceLocal {
	public void addStore(String store_name)  ;
	public Store liststorebydate(int store_id);
	public  double distance( 
            double latstore, 
                         double lonstore) ;
	
	public Store findbyId(int store_id);
	
	public String datestore(int store_id);
	public void deleteStore(int store_id);
	public List<Store> searchForstore(String store_name);
	public int updateStore(int store_id, Timestamp end, Timestamp start,String store_city, String store_name);

	public List<Store> allStores();
}
