package cn.hellobus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter implements IWriter
{
	private File m_file = null;
	private FileOutputStream m_outputStream = null;
	
	public FileWriter()
	{
	}
	
	public boolean open(String cityName, String cityCode)
	{
    	String curPath = System.getProperty("user.dir");
    	String fileFullPath = curPath + File.separator + cityName + "(" + cityCode + ").txt";
		try
		{
			m_file = new File(fileFullPath);
		}
		catch(NullPointerException exception)
		{
			return false;
		}
		
		return true;
	}
	
	public void close()
	{
		try
		{
			if(m_outputStream != null)
			{
				m_outputStream.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isExist()
	{
		return m_file.exists();
	}
	
	public int write(String content)
	{
		try
		{
			m_outputStream = new FileOutputStream(m_file, true);
			m_outputStream.write(content.getBytes("utf-8"));
		}
		catch(FileNotFoundException exception)
		{
			System.out.println("file : " + m_file.toString() + " is not found!");
		}
		catch (Exception exception)
		{
			System.out.println("file : " + m_file.toString() + " exception!");
		}
		
		return 1;
	}
}
