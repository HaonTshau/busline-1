package cn.hellobus;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.hellobus.config.CityNameConfig;

public class BuslineSpider
{
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
	
	public static void work(String province, List<String> cityList)
	{
        try
        {
        	for(String city : cityList)
        	{
        		int cityAreaCodeStartIndex = city.indexOf("(");
        		int cityAreaCodeEndIndex = city.indexOf(")");
        		String cityName = city.substring(0, cityAreaCodeStartIndex);
        		String cityAreaCode = city.substring(cityAreaCodeStartIndex + 1, cityAreaCodeEndIndex);
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
	}
	
	public static void spide()
	{
    	if(!initConfig())
    	{
    		System.out.println("init config error!");
    		return;
    	}
    	
    	Map<String, List<String>> provinceCityMap = CityNameConfig.getInstance().getProvinceCityMap();
    	for(Map.Entry<String, List<String>> entry : provinceCityMap.entrySet())
    	{
    		work(entry.getKey(), entry.getValue());
    	}
	}
}
