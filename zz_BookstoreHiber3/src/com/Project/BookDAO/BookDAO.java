package com.Project.BookDAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.Project.Model.Book;

@Component
public class BookDAO //extends BaseDao
{
	@PersistenceContext
	private EntityManager em;
	
	public void insertBook(Book book)
	{
		em.persist(book);
//		try {
//			em = getEntityManager();
//			em.getTransaction().begin();
//			em.persist(book);
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeEntityManager();
//		}
	}
	
	public List<Book> listAllBooks()
	{
		List<Book> list = new ArrayList<Book>();
		list = (List<Book>)em.createQuery("from Book").getResultList();
		return list;
//		try {
//			em = getEntityManager();
//			em.getTransaction().begin();
//			Query query = em.createQuery("from Book");
//			list = (List<Book>) query.getResultList();
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeEntityManager();
//		}
//		return list;
	}
	
	public void deleteBook(Integer book)
	{
		Book book1 = em.find(Book.class, book);
		em.remove(book1);
//		try {
//			em = getEntityManager();
//			em.getTransaction().begin();
//			Book book1 = em.find(Book.class, book.getId());
//			em.remove(book1);
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeEntityManager();
//		}
	}
	
	public void updateBook(Book book) 
	{   
		em.merge(book);
//		try {
//			em = getEntityManager();
//			em.getTransaction().begin();
//			em.merge(book);
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeEntityManager();
//		}
    }
	
	public Book getBook(Integer id) 
	{
        Book book1 = em.find(Book.class, id);
        return book1;
//        try {
//        	em = getEntityManager();
//        	book1 = em.find(Book.class, id);
//        } catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeEntityManager();
//		}
//        return book1;
     }
	
}
