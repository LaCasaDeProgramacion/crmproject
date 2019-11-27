package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Remote;

import crm.entities.User;
import crm.entities.prospecting.CarBrand;


@Remote
public interface ICarBrandRemote {

	
	public List<CarBrand> allBrands();
	public List<CarBrand> searchForBrand(String name);
	public boolean addCarBrand(String brand) ;
	public int deleteCarBrand(int id);
	public int updateCarBrand(CarBrand brand) ;
	public CarBrand getBrandById(int id);
}
