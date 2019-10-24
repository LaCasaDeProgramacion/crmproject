package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.Product;
import crm.entities.Promotion;


@Remote
public interface IPromotionServiceRemote {
	public int addPromotion(Promotion promotion);
	public void assignProductTopromotion(int productId, int promotionId);
	public void removePromotion(int id);
	public Promotion updatePromotion(Promotion promotion);
	public Promotion findPromotionById(int promotion);
	public List<Promotion> findAllPromotion();
	public List<Promotion> searchPromotion(String Promotiontext);//Complicated Search 
	public Promotion getPromotionbyproductid(int productid);
    public Promotion disablepromotion(Promotion promotion);
    public Product displayProductbypromotion(Promotion promotion);
	public List<Promotion> disablepromotionshowed(List<Promotion> promotion); 
	public List<Promotion> promotionenbaledtouse();
                            
	
	
}
