package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.exception.ValidationException;
import org.agoncal.application.petstore.util.Loggable;
import org.tomitribe.drycrud.persistence.Find;
import org.tomitribe.drycrud.persistence.Merge;
import org.tomitribe.drycrud.persistence.NamedQuery;
import org.tomitribe.drycrud.persistence.Persist;
import org.tomitribe.drycrud.persistence.PersistenceHandler;
import org.tomitribe.drycrud.persistence.QueryParam;
import org.tomitribe.drycrud.persistence.Remove;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Loggable
public abstract class CatalogService implements Serializable, InvocationHandler {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private EntityManager em;

    // ======================================
    // =              Public Methods        =
    // ======================================

    @Find
    public abstract Category findCategory(Long categoryId);


    @NamedQuery(Category.FIND_BY_NAME)
    public abstract Category findCategory(@QueryParam("pname") String categoryName);


    @NamedQuery(Category.FIND_ALL)
    public abstract List<Category> findAllCategories();


    @Persist
    public abstract Category createCategory(Category category);


    @Merge
    public abstract Category updateCategory(Category category);


    @Remove
    public abstract void removeCategory(Category category);

    public void removeCategory(Long categoryId) {
        removeCategory(findCategory(categoryId));
    }

    @NamedQuery(Product.FIND_BY_CATEGORY_NAME)
    public abstract List<Product> findProducts(String categoryName);


    public Product findProduct(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid id");

        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.getItems(); // TODO check lazy loading
        }
        return product;
    }

    @NamedQuery(Product.FIND_ALL)
    public abstract List<Product> findAllProducts();


    public Product createProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        if (product.getCategory() != null && product.getCategory().getId() == null)
            em.persist(product.getCategory());

        em.persist(product);
        return product;
    }

    @Merge
    public abstract Product updateProduct(Product product);


    @Remove
    public abstract void removeProduct(Product product);


    public void removeProduct(Long productId) {
        removeProduct(findProduct(productId));
    }

    @NamedQuery(Item.FIND_BY_PRODUCT_ID)
    public abstract List<Item> findItems(@QueryParam("productId") Long productId);


    @Find
    public abstract Item findItem(final Long itemId);


    public List<Item> searchItems(String keyword) {
        return _searchItems("%" + keyword.toUpperCase() + "%");
    }

    @NamedQuery(Item.SEARCH)
    protected abstract List<Item> _searchItems(@QueryParam("keyword") String keyword);


    @NamedQuery(Item.FIND_ALL)
    public abstract List<Item> findAllItems();


    public Item createItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        if (item.getProduct() != null && item.getProduct().getId() == null) {
            em.persist(item.getProduct());
            if (item.getProduct().getCategory() != null && item.getProduct().getCategory().getId() == null)
                em.persist(item.getProduct().getCategory());
        }

        em.persist(item);
        return item;
    }

    @Merge
    public abstract Item updateItem(Item item);


    @Remove
    public abstract void removeItem(Item item);


    public void removeItem(Long itemId) {
        removeItem(findItem(itemId));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return PersistenceHandler.invoke(this.em, method, args);
    }
}
