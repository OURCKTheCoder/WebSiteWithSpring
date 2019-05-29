package top.ourck.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	
	public static String getJSONString(int code) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", code);
		return jobj.toJSONString();
	}
	
	public static String getJSONString(int code, Map<String, Object> map) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", code);
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			jobj.put(entry.getKey(), entry.getValue());
		}
		return jobj.toJSONString();
	}
	
	public static String getJSONString(int code, String info) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", code);
		jobj.put("msg", info);
		return jobj.toJSONString();
	}
}
