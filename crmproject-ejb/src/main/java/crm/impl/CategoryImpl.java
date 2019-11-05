package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Category;
import crm.entities.Product;
import crm.entities.Roles;
import crm.interfaces.ICategoryServiceLocal;
import crm.interfaces.ICategoryServiceRemote;
import crm.utils.UserSession;
@Stateless
@LocalBean
public class CategoryImpl implements ICategoryServiceRemote,ICategoryServiceLocal {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<Category> allCategories() {
		Query q = em.createQuery("SELECT c FROM Category c ");
		return (List<Category>) q.getResultList();
	}

	@Override
	public Category searchForCategory(String categoryName) {
		Query q = em.createQuery("SELECT c FROM Category c where c.categoryName = :categoryName");
		q.setParameter("categoryName", categoryName);
	return (Category) q.getSingleResult();
	}

	@Override
	public void addCategory(String categoryName) {
		if(UserSession.getInstance().getRole()==Roles.VENDOR) {
		Category cat = new Category();
		cat.setCategory_name(categoryName);
		em.persist(cat);
		}
	}

	@Override
	public void deleteCategory(int category_id) {
		if(UserSession.getInstance().getRole()==Roles.VENDOR) {
		Query q = em.createQuery("DELETE FROM Category c WHERE c.category_id = :category_id");
        q.setParameter("category_id", category_id);
        q.executeUpdate();
		}
		
	}

	@Override
	public boolean updateCategory(Category category) {
		Query q = em.createQuery("UPDATE Category c SET c.category_name = :category_name WHERE c.category_id = :category_id");

		q.setParameter("category_id", category.getCategory_id());
		q.setParameter("category_name", category.getCategory_name());
		
		q.executeUpdate();
		return true;
	}

}
