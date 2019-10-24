package crm.interfaces;

import java.util.List;

import crm.entities.Category;

public interface ICategoryServiceLocal {
	public List<Category> allCategories();
	public Category searchForCategory(String categoryName);
	public void addCategory(String categoryName) ;
	public void deleteCategory(int id);
	public boolean updateCategory(Category category);
	
}
