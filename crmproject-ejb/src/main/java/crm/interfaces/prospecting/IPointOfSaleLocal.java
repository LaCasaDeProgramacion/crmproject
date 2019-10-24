package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Local;
import crm.entities.prospecting.Location;
import crm.entities.prospecting.PointOfSale;

@Local
public interface IPointOfSaleLocal {
	public List<PointOfSale> allPointOfSale();
	public List<PointOfSale>  searchForPointOfSale(String POSName);
	public void addPointOfSale(String name, float latitude, float longitude) ;
	public boolean deletePointOfSale(int id);
	public int updatePointOfSale(int id ,String name, float latitude, float longitude) ;

}
