package crm.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import crm.entities.Product;
import crm.entities.Store;
import io.ipinfo.api.errors.RateLimitedException;

public interface IStoreServiceRemote {
	public void addStore(String store_name)  ;
	public Store liststorebydate(int store_id);
	public  double distance( 
            double latstore, 
                         double lonstore) ;
	
	public Store findbyId(int store_id);
	
	public String datestore(int store_id);
	}
