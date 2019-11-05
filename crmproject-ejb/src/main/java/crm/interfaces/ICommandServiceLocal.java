package crm.interfaces;

import java.util.List;

import crm.entities.Commande;
import crm.entities.Product;
import crm.entities.User;

public interface ICommandServiceLocal {
	public Boolean createCommand(int product_id,int user_id);
	public Boolean createCmd(int product_id,int user_id);
	public Boolean addProductToCommand(int product_id,int user_id);
	public Boolean removeProductFromCommand(int product_id,int user_id);
	public Boolean AnnulCommand(int user_id);
	public List<Commande> allCommandByUser(int user_id);
	public List<Commande> CommandByUser(int user_id);
	public int removeProductFromComd(int product_id,int user_id);
	public int AnnulCmd(int user_id);
}
