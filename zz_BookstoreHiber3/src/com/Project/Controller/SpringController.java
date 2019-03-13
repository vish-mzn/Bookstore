package com.Project.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Project.BookDAO.BookDAO;
import com.Project.Model.Book;
import com.Project.ServiceLayer.BookService;

@Controller
public class SpringController
{
	@Autowired
	private BookService bookService;
//	BookDAO bookDAO = new BookDAO();
	
	@RequestMapping("/list")
	private ModelAndView listBook()
	{
		List<Book> listBook = bookService.listAll();

		return new ModelAndView("BookList", "listBook", listBook);
//        List<Book> listBook = bookDAO.listAllBooks();	
//        ModelAndView model = new ModelAndView();
//        model.setViewName("BookList");
//        model.addObject("listBook", listBook);
    }
	
	@RequestMapping("/new")
	private ModelAndView showNewForm() 
	{
		return new ModelAndView("BookForm", "newBook", new Book());
    }
	
	@RequestMapping("/edit/{id}")
	private ModelAndView showEditForm(@PathVariable("id") int id)
	{
//        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookService.getBook(id);
        
        return new ModelAndView("BookForm", "book", existingBook);
    }
	
	@RequestMapping("/insert")
	private ModelAndView insertBook(@ModelAttribute("newBook") Book bo)
	{
		bookService.add(bo);       
		return new ModelAndView("redirect:/list");
    }
	
	@RequestMapping("/update")
	private ModelAndView updateBook(@ModelAttribute("updatingBook") Book bo)
	{
//        int id = Integer.parseInt(request.getParameter("id"));
//        String title = request.getParameter("title");
//        String author = request.getParameter("author");
//        float price = Float.parseFloat(request.getParameter("price"));
//		  Book book = new Book(id, title, author, price);
		
        bookService.update(bo);       
		return new ModelAndView("redirect:/list");
    }
	
	@RequestMapping("/delete/{id}")
	private String deleteBook(@PathVariable("id") Integer id) 
	{
//        int id = Integer.parseInt(request.getParameter("id"));
//        Book book = new Book(id);
		
        bookService.delete(id);
        return "redirect:/list";
    }

}
