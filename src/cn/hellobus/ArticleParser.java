package cn.hellobus;

public class ArticleParser
{
	private String m_titleStartFlag = "<h1>";
	private String m_titleEndFlag = "</h1>";
	private String m_authorStartFlag = "<span>";
	private String m_authorEndFlag = "</span>";
	private String m_contentStartFlag = "<div class=\"article_text\">";
	private String m_contentEndFlag = "</div>";
	
	private String m_title = "default";
	private String m_author = "default";
	private String m_content = "default";
	
	public String getTitle()
	{
		return m_title;
	}
	
	public String getAuthor()
	{
		return m_author;
	}
	
	public String getContent()
	{
		return m_content;
	}
	
	boolean handleHtml(StringBuffer htmlString)
	{
        int start = -1;
        int end = -1;
        
        try
        {
            start = htmlString.indexOf(m_titleStartFlag) + m_titleStartFlag.length();
            end = htmlString.indexOf(m_titleEndFlag);
        	m_title = htmlString.substring(start, end);
        	
        	start = htmlString.indexOf(m_authorStartFlag, start) + m_authorStartFlag.length();
        	end = htmlString.indexOf(m_authorEndFlag, start);
        	m_author = htmlString.substring(start, end);
        	
        	start = htmlString.indexOf(m_contentStartFlag, start) + m_contentStartFlag.length();
        	start = htmlString.indexOf("<p>", start);
        	end = htmlString.indexOf(m_contentEndFlag, start);
        	m_content = htmlString.substring(start, end);
        }
        catch(StringIndexOutOfBoundsException exception)
        {
        	return false;
        }
        
		return true;
	}
}
