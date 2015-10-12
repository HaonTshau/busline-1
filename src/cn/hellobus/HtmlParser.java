package cn.hellobus;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser
{
	private List<String> m_buslineNameList = new ArrayList<String>();
	
	public List<String> getBuslineNameList()
	{
		return m_buslineNameList;
	}
	
	public boolean handleHtml(StringBuffer htmlString)
	{
        try
        {
        	int startDiv = htmlString.indexOf("<div class=\"inforB pad10\">");
        	String buslineContent = htmlString.substring(startDiv);
        	int endDiv = buslineContent.indexOf("</div>");
        	buslineContent = buslineContent.substring(0, endDiv);
        	
        	while(true)
        	{
        		int beforeBuslinePos = buslineContent.indexOf("target=\"_blank\">");
        		if(beforeBuslinePos < 0)
        		{
        			break;
        		}
        		
        		buslineContent = buslineContent.substring(beforeBuslinePos + "target=\"_blank\">".length());
        		int afterBuslinePos = buslineContent.indexOf("</a>");
        		if(afterBuslinePos < 0)
        		{
        			break;
        		}
        		
        		String buslineName = buslineContent.substring(0, afterBuslinePos);
        		m_buslineNameList.add(buslineName);
        	}
        }
        catch(StringIndexOutOfBoundsException exception)
        {
        	return false;
        }
        
		return true;
	}
}
