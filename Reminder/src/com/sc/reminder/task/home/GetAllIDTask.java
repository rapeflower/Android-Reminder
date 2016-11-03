package com.sc.reminder.task.home;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sc.reminder.common.base.BaseLogicTask;
import com.sc.reminder.common.base.BaseTaskInfo;
import com.sc.reminder.common.configure.Messages;
import com.sc.reminder.database.dao.ReminderDao;
import com.sc.reminder.model.memo.EventItem;
import com.sc.reminder.model.memo.NoteFile;
import com.sc.reminder.model.memo.TimeItem;
import com.sc.reminder.utils.CommonUtil;
import com.sc.reminder.utils.ScApplication;
import com.sc.reminder.utils.YzLog;
import com.sc.reminder.utils.net.RequestUtil;

public class GetAllIDTask extends BaseLogicTask<Messages> {
	public GetAllIDTask(BaseTaskInfo<Messages> taskInfo) {
		super(taskInfo);
	}

	@Override
	protected Messages execute() {
		Messages message = task.request();

		String ids = message.getResult();
		ReminderDao rd = new ReminderDao(task.context);
		ArrayList<String> _ids = rd.queryHasNoIDs(ids);
		YzLog.e("yz", "_ids size = " + _ids.size());
		for (String id : _ids) {
			YzLog.e("yz", "has no id = " + id);
			// 下载备忘录by id
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("json", "{'id':'" + id + "'}");
			RequestUtil ru = new RequestUtil();
			String result = ru.getReminderByID(params);
			// 替换192.168域名
			result = result.replaceAll(RequestUtil.LOCALIP,
					RequestUtil.SERVERIP);
			YzLog.e("yz", "cao result = " + result);
			// result =
			// "{'mid':'1','id':'1','title':'test','content':'test','star':'false','sound':'false','createtime':'13979349533878','issubmit':'true'}";
			// result =
			// "{'id':18,'aid':0,'title':'haha_title','content':'haha_cotent','star':0,'sound':1,'createTime':'2014-04-21 18:54:55','updateTime':'2014-04-21 18:54:55','noteFiles':[]}";
			// int max=2;
			// int min=1;
			// Random random = new Random();
			// int s = random.nextInt(max)%(max-min+1) + min;
			// YzLog.e("yz", "random = " + s);
			// if (s == 1) {
			// result = result.replaceAll("2014-04", "2014-05");
			// }
			// 将备忘录入库
			String mid = rd.addData(convert(result));
			YzLog.e("yz", "mid = " + mid);
		}
		message.setResult(String.valueOf(_ids.size()));
		HashMap<String, EventItem> items = rd.queryAll();
		try {
			JSONArray ja = JSON.parseArray(ids);

			Set<Entry<String, EventItem>> set = items.entrySet();
			Iterator<Entry<String, EventItem>> it = set.iterator();

			while (it.hasNext()) {
				boolean isMatch = false;
				Entry<String, EventItem> entry = it.next();
				for (int i = 0; i < ja.size(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					String _id = "";
					Object id = jo.get("id");
					if (id instanceof Integer) {
						_id = String.valueOf((Integer) id);
					}
					if (entry.getValue().getId().equals(_id)) {
						isMatch = true;
						break;
					}
				}

				if (!isMatch) {
					// items.remove(entry.getKey());
					rd.deleteData(entry.getKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		items = rd.queryAll();

		// 测试数据
		// Date date = new Date();
		// EventItem ei1 = new EventItem();
		// ei1.setMid("1");
		// ei1.setId("1");
		// ei1.setContent("ei1_content");
		// ei1.setCreatetime(String.valueOf(System.currentTimeMillis()));
		// ei1.setIssubmit("true");
		// ei1.setSound("false");
		// ei1.setStar("true");
		// ei1.setTitle("ei1_title1");
		// ei1.setUris("ei1_uris");
		// items.put(ei1.getMid(), ei1);
		// String result =
		// "{'id':'1','title':'test','content':'ahah','star':'false','sound':'true','createtime':'2013-12-12 00:00:00'}";
		// m.setResult(result);
		// Object[] objs = {CommonUtil.convert(items), items};
		// 设置全局数据

		HashMap<String, TimeItem> map = CommonUtil.getInstance().convert(
				items);
		YzLog.e("yz", "map size = " + map.size());
		ScApplication.getInstance().setTimeItems(map);
		ScApplication.getInstance().setResultList(items);
		return message;
	}

	public EventItem convert(String result) {
		EventItem ei = new EventItem();
		try {
			JSONObject jo = JSON.parseObject(result);
			ei.setMid("");
			ei.setId(String.valueOf(jo.getInteger("id")));
			ei.setAid(String.valueOf(jo.getInteger("aid")));
			ei.setTitle(jo.getString("title"));
			ei.setContent(jo.getString("content"));
			ei.setStar(String.valueOf(jo.getInteger("star")));
			ei.setSound(String.valueOf(jo.getInteger("sound")));
			ei.setCreatetime(jo.getString("createTime"));

			List<NoteFile> nfils = new ArrayList<NoteFile>();
			JSONArray ja = JSON.parseArray(jo.getString("noteFiles"));
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo2 = ja.getJSONObject(i);
				YzLog.e("yz", "cao jo2 = " + jo2.toString());
				NoteFile nf = new NoteFile();
				nf.setId(String.valueOf(jo2.getInteger("id")));
				nf.setRid(String.valueOf(jo2.getInteger("nid")));
				nf.setUris(jo2.getString("filePath"));
				RequestUtil ru = new RequestUtil();
				File file = ru.getReminderFile(jo2.getString("filePath"));
				nf.setMuris(file.getAbsolutePath());
				nf.setTime("1000");
				nfils.add(nf);
			}
			ei.setNoteFiles(nfils);
			ei.setIssubmit("true");
			ei.setSubmittype(EventItem.IDLE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ei;
	}
}
