package cn.hellobus;

public interface IWriter
{
	public boolean open(String author, String title);
	
	public void close();
	
	public boolean isExist();
	
	public int write(String content);
}
