package cn.hellobus;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBWriter implements IWriter
{
	private String m_dbDriver = "com.mysql.jdbc.Driver";
	private String m_dbUrl = "jdbc:mysql://192.168.80.130:3306/article?useUnicode=true&characterEncoding=utf-8";
	private String m_dbUser = "root";
	private String m_dbPassword = "123456";
	private Connection m_dbConn = null;
	private String m_author = null;
	private String m_title = null;
	
	public DBWriter()
	{
	}
	
	public boolean open(String author, String title)
	{
		try
		{
			Class.forName(m_dbDriver);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		
		try
		{
			m_dbConn = DriverManager.getConnection(m_dbUrl, m_dbUser, m_dbPassword);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		m_author = author;
		m_title = title;
		
		return true;
	}
	
	public void close()
	{
		try
		{
			if(!m_dbConn.isClosed())
			{
				m_dbConn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isExist()
	{
		boolean exist = false;
		String querySql = "select * from article.article_info where author = ? and title = ? limit 1";
		
		try
		{
			if(m_dbConn.isClosed())
			{
				System.out.println("db connection is closed!");
				return false;
			}
			
			PreparedStatement ps = m_dbConn.prepareStatement(querySql);
			ps.setBytes(1, m_author.getBytes("utf8"));
			ps.setBytes(2, m_title.getBytes("utf8"));
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				exist = true;
			}
			
			ps.close();
			rs.close();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return exist;
	}
	
	public int write(String content)
	{
		int affectRows = 0;
		String insertSql = "insert into article.article_info(author, title, content) values (?, ?, ?)";
		
		try
		{
			if(m_dbConn.isClosed())
			{
				System.out.println("db connection is closed!");
				return affectRows;
			}
			
			PreparedStatement ps = m_dbConn.prepareStatement(insertSql);
			ps.setBytes(1, m_author.getBytes("utf8"));
			ps.setBytes(2, m_title.getBytes("utf8"));
			ps.setBytes(3, content.getBytes("utf8"));
			affectRows = ps.executeUpdate();
			
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return affectRows;
	}
}
