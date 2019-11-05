package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Remote;
import crm.entities.prospecting.Location;
import crm.entities.prospecting.PointOfSale;


@Remote
public interface IPointOfSaleRemote {
	
	public List<PointOfSale> allPointOfSale();
	public List<PointOfSale>  searchForPointOfSale(String POSName);
	public boolean addPointOfSale(String name,  float latitude, float longitude) ;
	public int deletePointOfSale(int id);
	public int updatePointOfSale(int id ,String name, float latitude, float longitude) ;

	

}
