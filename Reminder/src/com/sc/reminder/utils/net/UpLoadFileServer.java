//package com.tydic.playerguide;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.multipart.FilePart;
//import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
//import org.apache.commons.httpclient.methods.multipart.Part;
//import org.apache.commons.httpclient.methods.multipart.StringPart;
//
//import android.util.Log;
//
///**
// * 
// * [һ�仰���ܼ���]<BR>
// * [������ϸ����] �ϴ�������
// * 
// * @author qudapeng
// * @version [PlayerGuide2, 2013-11-14]
// */
//public class UpLoadFileServer {
//	public static String sendDataByHttpClientPost(String path,
//			Map<String, String> params, List<File> filePath) throws Exception {
//
//		List<Part> partList = new ArrayList<Part>();
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			partList.add(new StringPart(entry.getKey(), entry.getValue(),
//					"UTF-8"));
//		}
//
//		for (int i = 0; i < filePath.size(); i++) {
//			String picPath = filePath.get(i).getPath();
//			Log.e("Other", "������ ͼƬ·��Service _ PicPath =" + picPath);
//			partList.add(new FilePart("file" + i, new File(filePath.get(i)
//					.getPath())));
//			Log.e("Image", "ͼƬ·��" + filePath.get(i).getPath());
//		}
//
//		// ʵ���ϴ���ݵ�����
//		// Part [] parts = {new StringPart("user", name),new FilePart("file",
//		// new File(filePath))};
//
//		Part[] parts = partList.toArray(new Part[0]);
//
//		Log.e("Other", "new Part[0] _size:" + parts.length);
//
//		PostMethod filePost = new PostMethod(path);
//
//		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost
//				.getParams()));
//
//		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
//		client.getParams().setContentCharset("UTF-8");
//		client.getHttpConnectionManager().getParams()
//				.setConnectionTimeout(5000);
//
//		int status = client.executeMethod(filePost);
//		Log.e("Other", "���ؽ��:" + status);
//		if (status == 200) {
//			System.out.println(filePost.getResponseCharSet());
//			Log.e("------->", filePost.getResponseCharSet() + "");
//			String result = new String(filePost.getResponseBodyAsString());
//			Log.e("------->", result);
//			return result;
//		} else {
//			throw new IllegalStateException("������״̬�쳣");
//		}
//	}
//
//	public static String sendFileByHttpClientPost(String path,
//			Map<String, String> params, List<File> filePath) throws Exception {
//
//		List<Part> partList = new ArrayList<Part>();
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			partList.add(new StringPart(entry.getKey(), entry.getValue(),
//					"UTF-8"));
//		}
//
//		for (int i = 0; i < filePath.size(); i++) {
//			String picPath = filePath.get(i).getPath();
//			Log.e("Other", "������ ͼƬ·��Service _ PicPath =" + picPath);
//			partList.add(new FilePart("File", new File(filePath.get(i)
//					.getPath())));
//			Log.e("Image", "ͼƬ·��" + filePath.get(i).getPath());
//		}
//
//		// ʵ���ϴ���ݵ�����
//		// Part [] parts = {new StringPart("user", name),new FilePart("file",
//		// new File(filePath))};
//
//		Part[] parts = partList.toArray(new Part[0]);
//
//		Log.e("Other", "new Part[0] _size:" + parts.length);
//
//		PostMethod filePost = new PostMethod(path);
//
//		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost
//				.getParams()));
//
//		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
//		client.getParams().setContentCharset("UTF-8");
//		client.getHttpConnectionManager().getParams()
//				.setConnectionTimeout(15000);
//
//		int status = client.executeMethod(filePost);
//		Log.e("Other", "���ؽ��:" + status);
//		if (status == 200) {
//			System.out.println(filePost.getResponseCharSet());
//			Log.e("------->", filePost.getResponseCharSet() + "");
//			String result = new String(filePost.getResponseBodyAsString());
//			Log.e("------->", result);
//			return result;
//		} else {
//			throw new IllegalStateException("������״̬�쳣");
//		}
//
//	}
//}
