package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Remote;
import crm.entities.prospecting.CarBrand;


@Remote
public interface ICarBrandRemote {

	
	public List<CarBrand> allBrands();
	public List<CarBrand> searchForBrand(String name);
	public boolean addCarBrand(CarBrand brand) ;
	public int deleteCarBrand(int id);
	public int updateCarBrand(CarBrand brand) ;
}
