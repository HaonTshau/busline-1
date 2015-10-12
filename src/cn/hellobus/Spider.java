package cn.hellobus;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.hellobus.config.CityNameConfig;

public class Spider
{
	public static final String m_requestUrl = "http://bus.mapbar.com/";
	public static final String m_requestPath = "/xianlu/";
	
    public void work(String province, List<String> cityList)
    {
        try
        {
        	for(String city : cityList)
        	{
        		int cityAreaCodeStartIndex = city.indexOf("(");
        		int cityAreaCodeEndIndex = city.indexOf(")");
        		String cityName = city.substring(0, cityAreaCodeStartIndex);
        		String cityAreaCode = city.substring(cityAreaCodeStartIndex + 1, cityAreaCodeEndIndex);
        		
        		HttpGetter httpGetter = new HttpGetter();
        		StringBuffer buffer = httpGetter.getHtml(m_requestUrl + cityName + m_requestPath);
        		
        		HtmlParser parser = new HtmlParser();
        		if(!parser.handleHtml(buffer))
        		{
        			System.out.println("parser error!{" + province + " : " + cityName + "}");
        			continue;
        		}
        		
        		IWriter writer = null;
        		//指定保存方式
        		boolean isSaveToDB = false;
        		if(isSaveToDB)
        		{
        			writer = new DBWriter();
        		}
        		else
        		{
        			writer = new FileWriter();
        		}
        		
        		if(!writer.open(cityName, cityAreaCode))
        		{
        			continue;
        		}
        		
        		if(writer.isExist())
        		{
        			writer.close();
        			System.out.println(cityName+ "-" + cityAreaCode + " is exist!");
        			continue;
        		}
        		
        		String buslineNames = "";
        		List<String> buslineNameList = parser.getBuslineNameList();
        		for(String buslineName : buslineNameList)
        		{
        			buslineNames += (buslineName + ",");
        		}
        		writer.write(buslineNames);
        		writer.close();
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
    
	public static boolean initConfig()
	{
		boolean result = false;
		try
		{
			result = CityNameConfig.getInstance().parser(System.getProperty("user.dir") + File.separator + "cityname_config.xml");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return result;
		}
		
		return result;
	}
	
    public static void main(String[] args)
    {
    	if(!initConfig())
    	{
    		System.out.println("init config error!");
    		return;
    	}
    	
    	Map<String, List<String>> provinceCityMap = CityNameConfig.getInstance().getProvinceCityMap();
    	for(Map.Entry<String, List<String>> entry : provinceCityMap.entrySet())
    	{
    		Spider spider = new Spider();
    		spider.work(entry.getKey(), entry.getValue());
    	}
    	
    	System.out.println("work done!");
    	System.exit(0);
    }
}
