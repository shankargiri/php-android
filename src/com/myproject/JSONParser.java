package com.myproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	private static final String LOGTAG = "JSON";
	private static InputStream is = null;
	private JSONObject jobj = null;
	private static String json = "";
	
	public JSONParser(){
		
	}
	
	public JSONObject getJSONFromUrl(String url){
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		}catch(UnsupportedEncodingException e){
			Log.i(LOGTAG, "The unsupported encoding exception is :" , e);
		}catch(ClientProtocolException e){
		Log.i(LOGTAG, "The protocol error is: " , e);
		}catch (IOException e) {
		Log.i(LOGTAG, "The IOException error is: " , e);
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null; 
			while((line =reader.readLine()) != null){
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.i(LOGTAG, "Error converting json to string" + e.toString());
		}
		try {
			jobj = new JSONObject(json);
		} catch (Exception e) {
			Log.i(LOGTAG, "Error converting to json object" + e.toString());
		}
		return jobj;
	}
	
	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
//		try {
//			if(method == "POST"){
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpPost httpPost = new HttpPost(url);
//				httpPost.setEntity(new UrlEncodedFormEntity(params));
//				HttpResponse httpResonse = httpClient.execute(httpPost);
//				HttpEntity httpEntity = httpResonse.getEntity();
//				is = httpEntity.getContent();
//			}else if (method == "GET"){
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				String paramString = URLEncodedUtils.format(params, "UTF-8");
//				url +="?" + paramString;
//				HttpGet httpGet = new HttpGet(url);
//				HttpResponse httpResponse = httpClient.execute(httpGet);
//				HttpEntity httpEntity = httpResponse.getEntity();
//				is = httpEntity.getContent();
//			}
//		} catch (UnsupportedEncodingException e) {
//			Log.i(LOGTAG, "UnsupportedEncodingException is ",e);
//		}catch(ClientProtocolException e){
//			Log.i(LOGTAG, "ClientProtocolException is ",e);
//		}catch(IOException e){
//			Log.i(LOGTAG, "IOException is ",e);
//		}
		
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//			StringBuilder sb = new StringBuilder();
//			String line = null; 
//			while((line = reader.readLine()) != null){
//				sb.append(line+"\n");
//			}
//			is.close();
//			json = sb.toString();
//		} catch (Exception e) {
//			Log.i(LOGTAG, "Buffer error is ",e);
//		}
//		
//		try {
//			jobj = new JSONObject(json);
//			
//		} catch (JSONException e) {
//			Log.i(LOGTAG, "JSON Parser error is ",e);
//		}
		return jobj;
	}
}





