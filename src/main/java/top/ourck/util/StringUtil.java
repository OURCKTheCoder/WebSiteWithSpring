package top.ourck.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	private static final String SP_CHARS_PATTERN =
			"\"[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t\"";
	
	public static boolean containsSpchar(String str) {
		Pattern p = Pattern.compile(SP_CHARS_PATTERN);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}
}
