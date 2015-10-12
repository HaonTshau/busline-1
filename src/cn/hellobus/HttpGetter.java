package cn.hellobus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetter
{
    private URL requestURL;
    private HttpURLConnection httpURLConn;
    
    public StringBuffer getHtml(String url)
    {
    	StringBuffer buffer = new StringBuffer();
    	try
    	{
    		requestURL = new URL(url);
    		httpURLConn= (HttpURLConnection)requestURL.openConnection();
    		httpURLConn.setDoOutput(true);
    		httpURLConn.setRequestMethod("GET");
    		httpURLConn.setIfModifiedSince(999999999);
    		
            httpURLConn.connect();
            InputStream in =httpURLConn.getInputStream();
            BufferedReader bd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            
            String temp = new String();
            while((temp = bd.readLine()) != null)
            {
                buffer.append(temp);
            }
    	}
    	catch(Exception exception)
    	{
    		System.out.println("http request exception!");
    	}
        finally
        {
            if(httpURLConn!=null)
            {
                httpURLConn.disconnect();
            }
        }
    	
    	return buffer;
    }
}
