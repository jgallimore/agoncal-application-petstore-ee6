package org.agoncal.application.petstore.persistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class BookCrud implements InvocationHandler {
	
	@PersistenceContext
    private EntityManager em;
	
	@Persist
	public abstract Book create(final Book book);
	
	@Merge
	public abstract Book update(final Book book);
	
	@NamedQuery(Book.FIND_BY_TITLE)
	@Optional
	public abstract List<Book> findBooksByTitle(
			@QueryParam("title") final String title, 
			@Offset final Integer offset, 
			@MaxResults final Integer max);

	@NamedQuery(Book.FIND_ALL)
	@Optional
	public abstract List<Book> findAll(@Offset final Integer offset, @MaxResults final Integer max);
	
	@NamedQuery(update = true, value = Book.UPDATE_BOOKS_SET_YEAR)
	public abstract void setYearOnAllBooks(@QueryParam("year") final Long year);

	@NamedQuery(update = true, value = Book.UPDATE_BOOKS_SET_YEAR)
	public abstract String badUpdate(@QueryParam("year") final Long year);

	
	@NamedQuery(update = true, value = Book.DELETE_ALL)
	public abstract int deleteAll();
	
	@Find
	public abstract Book find(Long id);
	
	@NamedQuery(Book.FIND_BY_ID)
	public abstract Book findById(@QueryParam("id") Long id);
	
	@NamedQuery(Book.FIND_BY_ID)
	@Optional
	public abstract Book optionalFindById(@QueryParam("id") Long id);
	
	public abstract List<Book> dummy();

	public void deleteAllAndAdd(Book... books) {
		this.deleteAll();
		for (final Book book : books) {
			this.create(book);
		}		
	}

	@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return PersistenceHandler.invoke(this.em, method, args);
    }
}
