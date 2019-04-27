package com.nowcoder.beans;
import java.util.HashMap;
import java.util.Map;

/**
 * From NowCoder.
 * @author NowCoder
 */
public class ViewObject {
	
    public Map<String, Object> objs = new HashMap<String, Object>();
    
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }

	@Override
	public String toString() {
		return "ViewObject [objs=" + objs + "]";
	}
    
}