package com.Project.BookDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Project.Model.Book;

public class BookDAO 
{
	/*private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;*/
	Connection con=null;
	
	/*public BookDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }*/
	
	protected void connect() throws SQLException
	{
		if(con == null || con.isClosed())
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstorehibernate", "root", "root");
		}
	}
	protected void disconnect() throws SQLException
	{
		if(con != null && !con.isClosed()){
			con.close();
		}
	}
	
	public boolean insertBook(Book book) throws SQLException
	{
		connect();
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO book (title, author, price) VALUES (?, ?, ?)");
		ps.setString(1, book.getTitle());
		ps.setString(2, book.getAuthor());
		ps.setFloat(3, book.getPrice());
		
		boolean rowInserted = ps.executeUpdate() > 0;
		ps.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Book> listAllBooks() throws SQLException
	{
		List<Book> list = new ArrayList<Book>();
		connect();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM book");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			int id = rs.getInt(1);
			String author = rs.getString(2);
			float price = rs.getFloat(3);
			String title = rs.getString(4);
			
			Book book = new Book(id, title, author, price);
			list.add(book);
		}
		rs.close();
		ps.close();
		disconnect();
		
		return list;
	}
	
	public boolean deleteBook(Book book) throws SQLException
	{
		connect();
		PreparedStatement ps = con.prepareStatement("DELETE FROM book where id = ?");
		ps.setInt(1, book.getId());
		
		boolean rowDeleted = ps.executeUpdate() > 0;
		ps.close();
		disconnect();
		return rowDeleted;
	}
	
	public boolean updateBook(Book book) throws SQLException 
	{
        connect();
         
        PreparedStatement ps = con.prepareStatement("UPDATE book SET title = ?, author = ?, price = ? WHERE id = ?");
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setFloat(3, book.getPrice());
        ps.setInt(4, book.getId());
         
        boolean rowUpdated = ps.executeUpdate() > 0;
        ps.close();
        disconnect();
        return rowUpdated;     
    }
	
	public Book getBook(int id) throws SQLException 
	{
        Book book = null;
        connect();
         
        PreparedStatement statement = con.prepareStatement("SELECT * FROM book WHERE id = ?");
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
             
            book = new Book(id, title, author, price);
        }
        resultSet.close();
        statement.close();
         
        return book;
     }
	
}
