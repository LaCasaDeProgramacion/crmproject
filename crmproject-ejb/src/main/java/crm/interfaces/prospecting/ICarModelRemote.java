package crm.interfaces.prospecting;

import java.util.List;
import javax.ejb.Remote;

import crm.entities.prospecting.CarBrand;
import crm.entities.prospecting.CarModel;

@Remote
public interface ICarModelRemote {
	public List<Object> BrandsOfModel(int idModel); 
	public List<CarModel> allModels();
	public List<CarModel> searchForModel(String model);
	public boolean addCarModel(String model, int idBrand) ;
	public boolean deleteCarModel(int id);
	public int updateCarModel(String model, int id) ;

}
