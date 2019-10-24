package crm.interfaces;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.Product;




@Remote
public interface IPackServiceRemote {
	public void addpack(Pack pack);
	public Pack updatepack(Pack pack);
	public Set<Product> assignproducttopack(List<Integer>Products ,int packid);
	public void removePack(int id);
	public void removeProductfrompack(Product product);
	public List<Pack> searchPack(String Packtext);
	public List<Product> findproductsPack(Pack pack);
	public void disableepackforclient(Pack pack);
	public double calculatepackprice(Pack pack);
	public void archivepack(int idpack);
}
