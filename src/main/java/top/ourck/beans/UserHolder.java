package top.ourck.beans;

import org.springframework.stereotype.Component;

/**
 * 使用ThreadLocal存储的User，以在保证变量可共享的同时提供并发访问的安全性。<br>
 * <b>为什么要使用ThreadLocal存储待使用的User？</b>
 * <br>
 * <ul>
 * <li>
 * 因为不止一个线程工作在服务器上，
 * 而又有“每个线程处理自己的登陆会话”这一需求，
 * 所以使用线程自己的ThreadLocal来存储User对象。
 * </li>
 * 
 * <li>
 * 而与此同时，作为@Component的{@link top.ourck.interceptor.PassportInterceptor}是单例的。
 * 这样直接搞一个共享的User变量的话很不线程安全。
 * </li>
 * 
 * <li>
 * 另一方面，Interceptor与Controller是两个模块（两个类），他们之间无法直接共享变量。
 * 这时候把东西（User对象）让执行者（线程）自己拿着，到了合适的地方在取出来。
 * </li>
 * </ul>
 * @author ourck
 */
@Component
public class UserHolder {

	private static ThreadLocal<User> userTl = new ThreadLocal<>();
	
	public User getUser() { return userTl.get(); }
	public void setUser(User user) { userTl.set(user); }
	
	/**
	 * TODO ThreadLocal的变量必须手动释放 否则内存泄露!!!
	 * http://ifeve.com/%E4%BD%BF%E7%94%A8threadlocal%E4%B8%8D%E5%BD%93%E5%8F%AF%E8%83%BD%E4%BC%9A%E5%AF%BC%E8%87%B4%E5%86%85%E5%AD%98%E6%B3%84%E9%9C%B2/
	 */
	public void clear() {
		userTl.remove();
	} 
}
