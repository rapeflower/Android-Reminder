package com.sc.reminder.utils.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sc.reminder.log.LogUtils;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.Request.RequestMethod;


public class HttpConnector {
	
	boolean isDialog;
	boolean isYzNetRequestExtra = false;
	String extraData = "";
	
	public HttpConnector(boolean isDialog) {
		this.isDialog = isDialog;
	}
	
	public String execute(Request request) {
		
		if (isDialog) {
			ScApplication.getInstance().showLoadingDialog();
		}
		
		String result = null;
		HttpResponse response = null;
		
		try {
			
			if (!ScApplication.getInstance().checkNet()) {
//				ScApplication.getInstance().showToast("暂无网络");
				return "";
			}
			
			String url = request.getUrl();
	        
	        DefaultHttpClient client = new DefaultHttpClient();
		    client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			if (request.getRequest_method() == RequestMethod.POST) {
				HttpPost httppost = new HttpPost(url);
				Log.d("yz", "post url = " + httppost.getURI());
				
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("param", "{'version':'1.0','token':'75bn222qlc2i8iifn3up3fqes503a4sptjj'}"));
				if (null != request.getParams() && request.getParams().size() != 0) {
					Set<Entry<String, String>> set = request.getParams().entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						if ("yzNetRequestExtra".equals(entry.getKey())) {
							isYzNetRequestExtra = true;
							extraData = entry.getValue();
						}
						else {
							parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
							
							Log.e("yz", "请求参数:" + entry.getKey() + " = " + entry.getValue());							
						}
					}
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
				httppost.setEntity(entity);
				response = client.execute(httppost);				
			}
			else {
				if (null != request.getParams() && request.getParams().size() != 0) {
					Set<Entry<String, String>> set = request.getParams().entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						if ("yzNetRequestExtra".equals(entry.getKey())) {
							isYzNetRequestExtra = true;
							extraData = entry.getValue();
						}
						else {
							url += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue());
						}
					}
				}
				HttpGet httpGet = new HttpGet(url);
				Log.d("yz", "get url = " + httpGet.getURI());
				response = client.execute(httpGet);
			}
			int code = response.getStatusLine().getStatusCode();
			if(code == 200){
				result = EntityUtils.toString(response.getEntity());
				Log.d("yz", "this result = " + result);
//				try {
//					JSON.parseObject(result);
//				} catch (Exception e) {
//					result = "{\"code\":\"1010\"}";
//					throw new IllegalStateException("服务器状态异常");
//				}
			}
			else {
				result = EntityUtils.toString(response.getEntity());
				Log.d("yz", "code = " + code + ",result = " + result);
//				throw new IllegalStateException("服务器返回异常");
			}
			if (isDialog) {
//				ScApplication.getInstance().dismissLoadingDialog();
			}
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			if (isDialog) {
				ScApplication.getInstance().dismissLoadingDialog();
			}
			e.printStackTrace();
//			result = "{\"code\":\"1011\"}";//网路状态异常标记
//			Looper.prepare();
//			Toast.makeText(context, "网路状态异常", Toast.LENGTH_LONG).show();
//			Looper.loop();
		} catch (IOException e) {
			if (isDialog) {
				ScApplication.getInstance().dismissLoadingDialog();
			}
			e.printStackTrace();
//			result = "{\"code\":\"1009\"}";//超时标记
//			Looper.prepare();
//			Toast.makeText(context, "网络连接超时", Toast.LENGTH_LONG).show();
//			Looper.loop();
		} catch (Exception e) {
			if (isDialog) {
				ScApplication.getInstance().dismissLoadingDialog();
			}
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String execute(String path, String id, String filePath){
		String result = "";
		try {
			List<Part> partList = new ArrayList<Part>();
			partList.add(new StringPart("param", "{'version':'1.0','token':'75bn222qlc2i8iifn3up3fqes503a4sptjj'}", "UTF-8"));
			partList.add(new StringPart("json", "{'nid':'"+id+"'}", "UTF-8"));
			partList.add(new FilePart("file", new File(filePath)));
			YzLog.e("yz", "sc id = " + id);
			YzLog.e("yz", "sc filePath = " + filePath);
			// ʵ���ϴ���ݵ�����
			// Part [] parts = {new StringPart("user", name),new FilePart("file",
			// new File(filePath))};
	
			Part[] parts = partList.toArray(new Part[0]);
	
			YzLog.e("Other", "new Part[0] _size:" + parts.length);
	
			PostMethod filePost = new PostMethod(path);
	
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost
					.getParams()));
	
			org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
			client.getParams().setContentCharset("UTF-8");
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
	
			int status = client.executeMethod(filePost);
			Log.e("Other", "status = " + status);

			if (status == 200) {
				YzLog.e("------->", filePost.getResponseCharSet() + "");
				result = new String(filePost.getResponseBodyAsString());
				YzLog.e("------->", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}	
	
	public File execute(String path) {
		//准备拼接新的文件名（保存在存储卡后的文件名）
		String sdDir = Environment.getExternalStorageDirectory().getPath()
				+ "/reminder/record/";
		
		File dfile = new File(sdDir);
		if (!dfile.exists()) {
			boolean isSuccess = dfile.mkdirs();
			if (!isSuccess) {
				YzLog.e("yz", "创建文件夹"+sdDir+"失败");
				return null;
			}
		}	
		
		File file = new File(dfile, new SimpleDateFormat("HHmmss").format(new Date()) + ".mp3");
		if(file.exists()){
		    file.delete();
		}
		InputStream is = null;
		OutputStream os = null;
		try {
	         URL url = new URL(path);
	         URLConnection con = url.openConnection();
	         is = con.getInputStream();  
	         byte[] bytes = new byte[1024];   
	         int len;   
	         os = new FileOutputStream(file);   
	         while ((len = is.read(bytes)) != -1) {   
	             os.write(bytes, 0, len);   
	         }  
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
	        try {
	        	if (null != os) {
	        		os.close();
	        	}
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		
		return file;
	}
	
	//volley
	
	public void execute_volley(String url, final HashMap<String, String> params, Listener<String> listener, ErrorListener errorListener) {
		
		ErrorListener _el = null;
		
		if (null == errorListener) {
			_el = new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					arg0.printStackTrace();
					
					ScApplication.getInstance().showToast("请求失败！");
				}
			};
		}
		else {
			_el = errorListener;
		}
		
		StringRequest sr = new StringRequest(Method.POST, url, listener, _el) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> _params  = new HashMap<String, String>();
				_params.put("param", "{'version':'1.0','token':'75bn222qlc2i8iifn3up3fqes503a4sptjj'}");
				if (null != params) {
					_params.putAll(params);
				}
				return _params;
			}
			
			@Override
			public RetryPolicy getRetryPolicy() {
				RetryPolicy retryPolicy = new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);  
				return retryPolicy;  
			}
		};
		ScApplication.getInstance().getRequestQueue().add(sr);
	}
	
}
