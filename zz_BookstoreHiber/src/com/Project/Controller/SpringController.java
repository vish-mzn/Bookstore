package com.Project.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Project.BookDAO.BookDAO;
import com.Project.Model.Book;

@Controller
public class SpringController
{
	BookDAO bookDAO = new BookDAO();
	
	@RequestMapping("/list")
	private ModelAndView listBook()
	{
        List<Book> listBook = bookDAO.listAllBooks();
        
        ModelAndView model = new ModelAndView();
        model.setViewName("BookList");
        model.addObject("listBook", listBook);
        
        return model;
    }
	
	@RequestMapping("/new")
	private String showNewForm(Model model) 
	{
		Book book = new Book(); // creating a new BOOK object
		model.addAttribute("newBook", book);
		
		return "BookForm";
    }
	
	@RequestMapping("/edit")
	private ModelAndView showEditForm(HttpServletRequest request)
	{
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.getBook(id);
        
        return new ModelAndView("BookForm", "book", existingBook);
    }
	
	
	@RequestMapping("/insert")
	private String insertBook(@ModelAttribute("newBook") Book bo, Model model)
	{
//        String title = request.getParameter("title");
//        String author = request.getParameter("author");
//        float price = Float.parseFloat(request.getParameter("price"));
 
        Book newBo = new Book(bo.getTitle(), bo.getAuthor(), bo.getPrice());
        bookDAO.insertBook(newBo);
        model.addAttribute("newBook1", newBo);       
		return "redirect:/list";
    	
//    	RequestDispatcher rd = request.getRequestDispatcher("jsp/BookList.jsp");
//    	rd.forward(request, response);
    }
	
	@RequestMapping("/update")
	private String updateBook(HttpServletRequest request, Model model)
	{
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
 
        Book book = new Book(id, title, author, price);
        bookDAO.updateBook(book);
        model.addAttribute("newBook1", book);       
		return "redirect:/list";
    }
	
	
	@RequestMapping("/delete")
	private String deleteBook(HttpServletRequest request) 
	{
        int id = Integer.parseInt(request.getParameter("id"));
 
        Book book = new Book(id);
        bookDAO.deleteBook(book);
        return "redirect:/list";
    }

}
