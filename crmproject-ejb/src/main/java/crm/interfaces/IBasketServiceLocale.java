package crm.interfaces;

public interface IBasketServiceLocale {
    public void addProductToBasket(int basket_id,int product_id);
    public void removeProductFromBasket(int basket_id,int product_id);
    public void addCommandToBasket();
    public void createBasket(int user_id);
}