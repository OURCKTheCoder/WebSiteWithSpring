package top.ourck.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	
	public static String getJSONString(int code) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		return new JSONObject(map).toJSONString();
	}
	
	public static String getJSONString(int code, Map<String, Object> map) {
		map.put("code", code);
		return new JSONObject(map).toJSONString();
	}
	
	public static String getJSONString(int code, String info) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		map.put("msg", info);
		return new JSONObject(map).toJSONString();
	}
}
