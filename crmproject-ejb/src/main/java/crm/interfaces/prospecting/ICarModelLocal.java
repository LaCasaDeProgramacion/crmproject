package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Local;

import crm.entities.prospecting.CarBrand;
import crm.entities.prospecting.CarModel;


@Local
public interface ICarModelLocal {
	
	public List<Object> BrandsOfModel(int idModel); 
	public List<CarModel> allModels();
	public List<CarModel> searchForModel(String model);
	public int addCarModel(String model, int idBrand) ;
	public int deleteCarModel(int id);
	public int updateCarModel(String model, int id) ;

}
