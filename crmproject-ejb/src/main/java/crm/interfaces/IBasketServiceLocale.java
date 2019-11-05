package crm.interfaces;

import crm.entities.User;

import java.util.List;
import java.util.Set;


import crm.entities.Product;

public interface IBasketServiceLocale {
    public void addProductToBasket(int basket_id,int product_id);
    public boolean removeProductFromBasket(int basket_id,int product_id);
    public void addCommandToBasket();
    public void createBasket(User user);
    public List<Product> allProductInBasket();
    public List<Product> allProductInBasketById(int id);
}
