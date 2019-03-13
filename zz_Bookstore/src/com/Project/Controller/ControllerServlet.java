package com.Project.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

import com.Project.BookDAO.BookDAO;
import com.Project.Model.Book;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	
	//final static Logger logger = Logger.getLogger(ControllerServlet.class);

	BookDAO bookDAO = new BookDAO();
    
    public ControllerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getServletPath();
		System.out.println(action);
		try 
		{	switch(action) 
            {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertBook(request, response);
                break;
            case "/delete":
                deleteBook(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateBook(request, response);
                break;
            default:
                listBook(request, response);
                break;
            }
        } 
		catch (SQLException ex) 
		{
            throw new ServletException(ex);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		//logger.debug("Entering createCustomer method");
        List<Book> listBook = bookDAO.listAllBooks();
        request.setAttribute("listBook", listBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/BookList.jsp");
        dispatcher.forward(request, response);
    }
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/BookForm.jsp");
        dispatcher.forward(request, response);
    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.getBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/BookForm.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);
 
    }
	
	private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
 
        Book newBook = new Book(title, author, price);
        if(bookDAO.insertBook(newBook))
        {
        	response.sendRedirect("list");
        	/*RequestDispatcher rd = request.getRequestDispatcher("jsp/BookList.jsp");
        	rd.forward(request, response);*/
        }
    }
	
	private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
 
        Book book = new Book(id, title, author, price);
        bookDAO.updateBook(book);
        response.sendRedirect("list");
    }
	
	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
 
        Book book = new Book(id);
        bookDAO.deleteBook(book);
        response.sendRedirect("list");
    }

}
