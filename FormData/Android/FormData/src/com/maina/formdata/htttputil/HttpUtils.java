package com.maina.formdata.htttputil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class HttpUtils implements IHttpUtils {

	public String PostRequest(String url, Hashtable<String, String> table) throws Exception {
		String message = "";
		Log.d("HttpUtils ", "PostRequest: url: " + url);

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		BufferedReader in = null;
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setStaleCheckingEnabled(params, false);
		    HttpConnectionParams.setConnectionTimeout(params, 300000);
		    HttpConnectionParams.setSoTimeout(params, 300000);

			HttpClient client = new DefaultHttpClient(params);
			
			HttpPost request = new HttpPost(url);
			
			if (table != null) {
				Enumeration<String> names = table.keys();
				while (names.hasMoreElements()) {
					String key = names.nextElement();
					String value = (String) table.get(key);
					String kv = "&" + key + "=" + value;
					Log.d("HttpUtils ", "PostRequest: " + kv);
					postParameters.add(new BasicNameValuePair(key, value));
				}
			}
			StringEntity entity = new StringEntity(postParameters.get(0).getValue());
			Log.d("HttpUtils ", "URL: " + url +"entity: " + entity);
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			//request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			ByteArrayOutputStream bStrm = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				bStrm.write(ch);
			}
			message = new String(bStrm.toByteArray());
			
			try{
				bStrm.flush();
				bStrm.close();
				Log.d("HttpUtils ", "HTTP RESPONSE ANDROID CHECK IN SERVICES: ");
			}catch (Exception e) {
				e.printStackTrace();
			}catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
			return message;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public String GetRequest(String url, Hashtable<String, String> table) throws Exception {
		String message = "";
		Log.d("HttpUtils ", "GetRequest: url: " + url);
		InputStream inputStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if (table != null) {
				Enumeration<String> names = table.keys();
				int x = 0;
				while (names.hasMoreElements()) {
					String key = names.nextElement();
					String value = (String) table.get(key);
					String kv = (x == 0 ? "?" : "&") + key + "=" + value;
					//String kv = "/"+value;
					Log.d("HttpUtils ", "GetRequest: " + kv);
					url += kv;
					x++;
				}
			}
			Log.d("HttpUtils ", "GetRequest final url: " + url);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			inputStream = httpResponse.getEntity().getContent();

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuilder stringBuilder = new StringBuilder();

			String bufferedStrChunk = null;

			while((bufferedStrChunk = bufferedReader.readLine()) != null){
				stringBuilder.append(bufferedStrChunk);
			}
			message = stringBuilder.toString();
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return message;
	}

}
