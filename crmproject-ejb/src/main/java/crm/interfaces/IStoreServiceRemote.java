package crm.interfaces;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import crm.entities.Product;
import crm.entities.Store;
import io.ipinfo.api.errors.RateLimitedException;

public interface IStoreServiceRemote {
	public void addStore(String store_name,  String store_image)  ;
	public Store liststorebydate(int store_id);
	public  double distance( 
            double latstore, 
                         double lonstore) ;
	
	public Store findbyId(int store_id);
	
	public String datestore(int store_id);
	public void deleteStore(int store_id);
	public List<Store> searchForstore(String store_name);
	public int updateStore(int store_id,String store_city, String store_name);;
	
	public List<Store> allStores();
	public List<Store> getNearestStore();
}
