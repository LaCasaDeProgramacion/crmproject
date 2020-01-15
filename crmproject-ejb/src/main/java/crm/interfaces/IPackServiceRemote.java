package crm.interfaces;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.Product;
import crm.entities.ProductsPack;




@Remote
public interface IPackServiceRemote {
	public Boolean addpack(Pack pack);
	public Pack updatepack(Pack pack, int idpack, List<Integer> Products);
	public Pack findpackbyid(int id);
	public Boolean assignproducttopack(List<Integer>Products ,int packid);
	public void removePack(int id);
	public List<Object> searchPack(String Packtext);
	public List<Product> findproductsPack(int idpack);

	
	public void archivepack(int idpack);
	public void unarchivingpack(int idpack);
    
	public List<Pack> availablepacks();
	public List<Pack> notavailablepacks();
	public List<Pack> packsNotArchived();
	public List<Pack> packsArchived();
	public List<Pack> availablepacksorderbypriceDESC();
	public List<Pack> availablepacksorderbypriceASC();
	
	public Product getProductbyId(int idprod);
	
	

	
}
