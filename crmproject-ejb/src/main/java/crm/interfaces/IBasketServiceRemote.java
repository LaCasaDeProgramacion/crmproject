package crm.interfaces;

public interface IBasketServiceRemote {
	public void addProductToBasket(int basket_id,int product_id);
    public void removeProductFromBasket(int basket_id,int product_id);
    public void addCommandToBasket();
    public void createBasket(int user_id);
}
