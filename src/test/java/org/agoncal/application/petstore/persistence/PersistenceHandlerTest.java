package org.agoncal.application.petstore.persistence;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.agoncal.application.petstore.exception.ValidationException;
import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ApplicationComposer.class)
public class PersistenceHandlerTest {

	@EJB
	private BookCrud crud;
	
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private UserTransaction tx;
    
    @Module
    public PersistenceUnit persistence() {
        PersistenceUnit unit = new PersistenceUnit("db-unit");
        unit.setJtaDataSource("database");
        unit.setNonJtaDataSource("databaseUnmanaged");
        unit.getClazz().add(Book.class.getName());
        unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
        return unit;
    }

    @Module
    public EjbJar beans() {
        EjbJar ejbJar = new EjbJar("movie-beans");
        ejbJar.addEnterpriseBean(new StatelessBean(BookCrud.class));
        return ejbJar;
    }

    @Configuration
    public Properties config() throws Exception {
        Properties p = new Properties();
        p.put("movieDatabase", "new://Resource?type=DataSource");
        p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("movieDatabase.JdbcUrl", "jdbc:hsqldb:mem:db");
        return p;
    }
	
    @Before
    public void setUp() throws Exception {
    	tx.begin();
    	entityManager.createQuery("delete from Book b").executeUpdate();
    	tx.commit();
    }
    
	@Test
	public void testPersist() throws Exception {
		final Book book1 = new Book();
		
		book1.setAuthor("Author");
		book1.setTitle("Title");
		book1.setYear(2014L);
		
		crud.create(book1);
		
		final List<Book> allBooks = crud.findAll(0, 50);
		Assert.assertNotNull(allBooks);
		Assert.assertEquals(1, allBooks.size());
		
		final Book book = allBooks.get(0);
		Assert.assertEquals("Author", book.getAuthor());
		Assert.assertEquals("Title", book.getTitle());
		Assert.assertNotNull(book.getId());
		Assert.assertEquals(new Long(2014), book.getYear());
	}
	
	@Test
	public void testMerge() throws Exception {
		final Book book1 = new Book();
		
		book1.setAuthor("Author");
		book1.setTitle("Title");
		book1.setYear(2014L);
		
		crud.create(book1);
		final Book book = crud.findAll(0, 50).get(0);
		
		book.setTitle("Test2");
		crud.update(book);
		
		final List<Book> allBooks = crud.findAll(0, 50);
		Assert.assertNotNull(allBooks);
		Assert.assertEquals(1, allBooks.size());
		
		final Book retrievedBook = allBooks.get(0);
		Assert.assertEquals("Author", retrievedBook.getAuthor());
		Assert.assertEquals("Test2", retrievedBook.getTitle());
		Assert.assertNotNull(retrievedBook.getId());
		Assert.assertEquals(new Long(2014), retrievedBook.getYear());
	}
	
	@Test
	public void testFinder() throws Exception {
		for (int i = 0; i < 10; i++) {
			final Book book = new Book();
			book.setAuthor("Author" + (i + 1));
			book.setTitle("Title" + (i + 1));
			book.setYear(new Long(1990 + i));
			
			crud.create(book);
		}
		
		final List<Book> page1 = crud.findAll(0, 5);
		Assert.assertEquals(5, page1.size());
		
		final List<Book> all = crud.findAll(0, 10);
		Assert.assertEquals(10, all.size());
	}
	
	@Test
	public void testUpdater() throws Exception {
		for (int i = 0; i < 10; i++) {
			final Book book = new Book();
			book.setAuthor("Author" + (i + 1));
			book.setTitle("Title" + (i + 1));
			book.setYear(new Long(1990 + i));
			
			crud.create(book);
		}
		
		crud.setYearOnAllBooks(2014L);
		
		final List<Book> all = crud.findAll(0, 10);
		for (final Book book : all) {
			Assert.assertEquals(new Long(2014L), book.getYear());
		}
	}
	
	@Test
	public void testDeletionAndInsert() throws Exception {
		for (int i = 0; i < 10; i++) {
			final Book book = new Book();
			book.setAuthor("Author" + (i + 1));
			book.setTitle("Title" + (i + 1));
			book.setYear(new Long(1990 + i));
			
			crud.create(book);
		}
		
		List<Book> allBooks = crud.findAll(0, 50);
		Assert.assertEquals(10, allBooks.size());
		
		final Book book1 = new Book();
		
		book1.setAuthor("Author");
		book1.setTitle("Title");
		book1.setYear(2014L);
		
		crud.deleteAllAndAdd(book1);
		
		allBooks = crud.findAll(0, 50);
		Assert.assertEquals(1, allBooks.size());
		
		final Book book = crud.findAll(0, 50).get(0);
		Assert.assertEquals("Author", book.getAuthor());
		Assert.assertEquals("Title", book.getTitle());
		Assert.assertNotNull(book.getId());
		Assert.assertEquals(new Long(2014), book.getYear());
	}
	
	@Test
	public void testFinderQuery() throws Exception {
		for (int i = 0; i < 10; i++) {
			final Book book = new Book();
			book.setAuthor("Author" + (i + 1));
			book.setTitle("Title" + (i + 1));
			book.setYear(new Long(1990 + i));
			
			crud.create(book);
		}
		
		final List<Book> findBooksByTitle = crud.findBooksByTitle("%10", 0, 50);
		Assert.assertEquals(1, findBooksByTitle.size());
		
		final Book book = findBooksByTitle.get(0);
		Assert.assertEquals("Author10", book.getAuthor());
		Assert.assertEquals("Title10", book.getTitle());
		Assert.assertNotNull(book.getId());
		Assert.assertEquals(new Long(1999), book.getYear());
	}
	
	@Test
	public void testFindByPrimaryKey() throws Exception {
		Book book1 = new Book();
		
		book1.setAuthor("Author");
		book1.setTitle("Title");
		book1.setYear(2014L);
		
		book1 = crud.create(book1);
		
		final Book retrievedBook = crud.find(book1.getId());
		Assert.assertEquals(book1.getId(), retrievedBook.getId());
		Assert.assertEquals(book1.getAuthor(), retrievedBook.getAuthor());
		Assert.assertEquals(book1.getTitle(), retrievedBook.getTitle());
		Assert.assertEquals(book1.getYear(), retrievedBook.getYear());
	}
	
	@Test
	public void testNonOptionalFinder() throws Exception {
		Book book1 = new Book();
		
		book1.setAuthor("Author");
		book1.setTitle("Title");
		book1.setYear(2014L);
		
		book1 = crud.create(book1);
		
		final Book retrievedBook = crud.findById(book1.getId());
		Assert.assertEquals(book1.getId(), retrievedBook.getId());
		Assert.assertEquals(book1.getAuthor(), retrievedBook.getAuthor());
		Assert.assertEquals(book1.getTitle(), retrievedBook.getTitle());
		Assert.assertEquals(book1.getYear(), retrievedBook.getYear());
		
		try {
			crud.findById(99999L);
			Assert.fail("Expected exception not thrown");
		} catch (final EJBException e) {
			final Exception causedByException = e.getCausedByException();
			Assert.assertTrue(causedByException instanceof NoResultException);
		}
		
		Assert.assertNull(crud.optionalFindById(99999L));
	}
	
	@Test
	public void testInvalidMethod() throws Exception {
		try {
			crud.dummy();
			Assert.fail("Expected exception not thrown");
		} catch (final EJBException e) {
			final Throwable causedByException = e.getCause();
			Assert.assertTrue(causedByException instanceof AbstractMethodError);
		}
	}
	
	@Test
	public void testNullPersist() throws Exception {
		try {
			crud.create(null);
			Assert.fail("Expected exception not thrown");
		} catch (final ValidationException e) {
		}
	}
	
	@Test
	public void testNullFind() throws Exception {
		try {
			crud.find(null);
			Assert.fail("Expected exception not thrown");
		} catch (final ValidationException e) {
		}
	}
	
	@Test
	public void testNullUpdate() throws Exception {
		try {
			crud.update(null);
			Assert.fail("Expected exception not thrown");
		} catch (final ValidationException e) {
		}
	}
	
	@Test
	public void testNullParam() throws Exception {
		try {
			crud.findBooksByTitle(null, 0, 50);
			Assert.fail("Expected exception not thrown");
		} catch (final ValidationException e) {
		}
	}
	
	@Test
	public void testBadUpdate() throws Exception {
		for (int i = 0; i < 10; i++) {
			final Book book = new Book();
			book.setAuthor("Author" + (i + 1));
			book.setTitle("Title" + (i + 1));
			book.setYear(new Long(1990 + i));
			
			crud.create(book);
		}
		
		try {
			crud.badUpdate(2014L);
			Assert.fail("Expected exception not thrown");
		} catch (final EJBException e) {
			final Exception causedByException = e.getCausedByException();
			Assert.assertTrue(causedByException instanceof IllegalArgumentException);
		}
	}
}
