package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Local;
import crm.entities.prospecting.CarBrand;

@Local
public interface ICarBrandLocal {


	public List<CarBrand> allBrands();
	public List<CarBrand> searchForBrand(String name);
	public boolean addCarBrand(CarBrand brand) ;
	public int deleteCarBrand(int id);
	public int updateCarBrand(CarBrand brand) ;
}
