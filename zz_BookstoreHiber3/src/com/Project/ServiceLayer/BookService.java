package com.Project.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Project.BookDAO.BookDAO;
import com.Project.Model.Book;

@Component
public class BookService 
{
	@Autowired
	private BookDAO bookDao;
	
	@Transactional(readOnly=true)
	public List<Book> listAll()
	{
		return bookDao.listAllBooks();
	}
	
	@Transactional
	public void add(Book bo) 
	{
		Book newBo = new Book(bo.getTitle(), bo.getAuthor(), bo.getPrice());
		bookDao.insertBook(newBo);
	}
	
	@Transactional
	public void update(Book book)
	{
		bookDao.updateBook(book);
	}
	
	@Transactional
	public void delete(Integer id)
	{
		bookDao.deleteBook(id);
	}
	
	public Book getBook(Integer id)
	{
		Book b = bookDao.getBook(id);
		return b;
	}
	
}
