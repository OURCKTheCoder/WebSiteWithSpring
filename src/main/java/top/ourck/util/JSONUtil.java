package top.ourck.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class JSONUtil {
	
	public static String getJSONString(int code) {
		Map<String, String> map = new HashMap<>();
		map.put("code", code + "");
		return new JSONObject(map).toString();
	}
	
	public static String getJSONString(int code, Map<String, String> map) {
		map.put("code", code + "");
		return new JSONObject(map).toString();
	}
	
	public static String getJSONString(int code, String info) {
		Map<String, String> map = new HashMap<>();
		map.put("code", code + "");
		map.put("info", info);
		return new JSONObject(map).toString();
	}
}
