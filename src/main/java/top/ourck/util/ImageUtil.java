package top.ourck.util;

public class ImageUtil {
	
	public static String IMAGE_SAVE_PATH = "/home/ourck/resources/image/"; // TODO Is this correct?
	public static String IMAGE_DOMAIN = "http://127.0.0.1:8080/news/"; // TODO Is this correct?
	public static String[] VALID_EXTNAMES = {"jpg", "png", "bmp", "jpeg"};
	
	public static boolean isValid(String extName) {
		for(String ext : VALID_EXTNAMES) { // TODO 对于小规模的数组，遍历反而比Hash要快。
			if(ext.equals(extName)) return true;
		}
		
		return false;
	}
}
