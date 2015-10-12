package cn.hellobus.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CityNameConfig
{
	private Map<String, List<String>> m_provinceCityMap = new HashMap<String, List<String>>();
	private static CityNameConfig m_instance = new CityNameConfig();
	
	public static CityNameConfig getInstance()
	{
		return m_instance;
	}
	
	public Map<String, List<String>> getProvinceCityMap()
	{
		return m_provinceCityMap;
	}
	
	public boolean parser(String path) throws Exception
	{
		SAXReader reader = new SAXReader();
		InputStream stream = null;
		try
		{
			stream = new FileInputStream(path);
		} 
		catch (FileNotFoundException e)
		{
			return false;
		}
		
		try
		{
			Document doc = reader.read(stream);
			//获取根结点
			Element country = doc.getRootElement();
			
			List<?> provinceList = country.elements("province");
			for(Object obj : provinceList)
			{
				Element provinceObj = (Element)obj;
				String provinceName = provinceObj.attributeValue("name");
				Element citysObj = provinceObj.element("citys");
				String citysString = citysObj.attributeValue("list");
				String[] citys = citysString.split(",");
				if(citys == null)
				{
					System.out.println(provinceName + "解析出错");
					continue;
				}
				
				List<String> cityList = new ArrayList<String>();
				for(String city : citys)
				{
					cityList.add(city);
				}
				m_provinceCityMap.put(provinceName, cityList);
			}
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
