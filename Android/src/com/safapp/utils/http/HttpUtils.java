package com.safapp.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;


public class HttpUtils implements IHttpUtils{

private static final String Tag = "HttpUtils";
	
	@Override
	public String PostRequest(String url, Hashtable<String, String> table) throws Exception {
		Log.d(Tag, "PostRequest");
		if(table == null){
			Log.d(Tag, "PostRequest: table IS NULL");
			return null;
		}
		String msg = null;
		BufferedReader in = null;
		try{
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setStaleCheckingEnabled(params, false);
			HttpConnectionParams.setConnectionTimeout(params, 300000);
			HttpConnectionParams.setSoTimeout(params, 300000);
			
			HttpClient httpClient = new DefaultHttpClient(params);
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(table.size());
			Log.d(Tag, "PostRequest pair size: "+ table.size());
			if(table != null && !table.isEmpty()){
				Enumeration<String> name = table.keys();
				while(name.hasMoreElements()){
					String key = name.nextElement();
					String value = table.get(key);
					nameValuePair.add(new BasicNameValuePair(key, value));
				}
			}
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(nameValuePair.get(0).getValue());
			httpPost.setEntity(entity);
			httpPost.addHeader("Content-type", "application/json");
			httpPost.addHeader("Accept", "application/json");
			
			HttpResponse response = httpClient.execute(httpPost);
			msg = getResponse(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(in != null){
				try {
					in.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return msg;
	}
		
	private String getResponse(BufferedReader bufferedReader) throws IOException{
		Log.d(Tag, "getResponse");
		StringBuilder builder = new StringBuilder();
		String chunk = null;
		while ((chunk = bufferedReader.readLine()) != null) {
			builder.append(chunk);
		}
		return builder.toString();
	}

	@Override
	public String GetRequest(String url, Hashtable<String, String> table) throws Exception {
		Log.d(Tag, "GetRequest");
		if(table == null){
			Log.d(Tag, "GetRequest: table IS NULL");
			return null;
		}
		String msg = null;
		try{
			Log.d(Tag, "GetRequest pair size: "+ table.size());
			String afterUlr = "";
			if(table != null && !table.isEmpty()){
				Enumeration<String> name = table.keys();
				int x = 0;
				while(name.hasMoreElements()){
					String key = name.nextElement();
					String value = table.get(key);
					afterUlr += (x == 0 ? "?" : "&") + key + "=" + URLEncoder.encode(value);
					x++;
				}
			}
			url += afterUlr;
			Log.d(Tag, "GetRequest full url: "+url);
			HttpClient httpClient = new DefaultHttpClient(); 
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Content-type", "application/json");
			httpGet.addHeader("Accept", "application/json");
			
			HttpResponse response = httpClient.execute(httpGet);
			msg = getResponse(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
		}catch(Exception e){e.printStackTrace();}
		finally{
			Log.d(Tag, "GetRequest just finalizing: ");
		}
		return msg;
	}

}
