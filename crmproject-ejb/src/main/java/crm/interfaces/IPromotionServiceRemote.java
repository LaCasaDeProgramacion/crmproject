package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Pack;
import crm.entities.Product;
import crm.entities.Promotion;


@Remote
public interface IPromotionServiceRemote {
	public int addPromotion(Promotion promotion);
	public Boolean assignProductTopromotion(int productId, int promotionId);
	public Boolean removePromotion(int id);
	public Promotion updatePromotion(Promotion promotion,int idpromot,int idproduct);
	public Promotion findPromotionById(int promotion);
	public List<Promotion> searchPromotion(String Promotiontext);//Complicated Search 
	
	
	public Promotion getPromotionbyproductid(int productid);
    public Promotion disablepromotion(Promotion promotion);
    public Promotion enabledpromotion(Promotion promotion);
    public Product displayProductbypromotion(Promotion promotion);
	
	public List<Promotion> AssignedPromotions();
	public List<Promotion> findAllPromotionusabled();
	public List<Promotion> promotionNotUsedYet();
	//give product id return promotion price : 
	public double productPromotionValue(int productid);     
	
	
	
	
}
