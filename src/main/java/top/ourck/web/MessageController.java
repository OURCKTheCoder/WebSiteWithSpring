package top.ourck.web;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.beans.ViewObject;

import top.ourck.beans.Message;
import top.ourck.beans.User;
import top.ourck.beans.UserHolder;
import top.ourck.service.MessageService;
import top.ourck.service.UserService;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/msg")
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger("logger");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService msgService;
	
	@Autowired
	private UserHolder userHolder;
	
	@RequestMapping("/add") // TODO POSTMapping!!!
	@ResponseBody
	public String addMessage(@RequestParam("fromId") int fromId,
						   @RequestParam("toId") int toId,
						   @RequestParam("content") String content) {
		Message msg = new Message();
		msg.setFromId(fromId);
		msg.setToId(toId);
		msg.setContent(content);
		msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId)
											: String.format("%d_%d", toId, fromId)); // TODO A conversation saves message transferred in both way!!!
		msg.setCreatedDate(new Date());
		msg.setHasRead(0);
		msgService.addMessage(msg);
		
		return JSONUtil.getJSONString(0, msg.getId() + "");
	}
	
	@RequestMapping("list")
	public String showList(Model model) {
		User user = userHolder.getUser();
		if(user == null)
			return "redirect:/"; // Go and login.
		
		int localUid = user.getId();
		List<Message> msgList = msgService.getConversationList(localUid, 0, 100);
		List<ViewObject> conversationList = new LinkedList<>();
		for(Message m : msgList) {
			ViewObject vo = new ViewObject();
			vo.set("message", m);
            vo.set("headUrl", user.getImage());
            vo.set("userName", user.getName());
            vo.set("targetId", localUid == m.getFromId() ? m.getToId() : m.getFromId()); // TODO ???
            vo.set("totalCount", m.getId()); // TODO ???
            vo.set("unreadCount", msgService.getUnreadCountAt(localUid, m.getConversationId()));
            conversationList.add(vo);
		}
		model.addAttribute("conversations", conversationList);
		return "letter" ;
	}
	
	@RequestMapping("/detail")
	public String showDetail(@RequestParam("conversationId") String conversationId, Model model) {
		try {
            List<ViewObject> messages = new LinkedList<>();
            List<Message> conversationList = msgService.getConversationDetail(conversationId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getImage());
                vo.set("userName", user.getName());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
            return "letterDetail";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letterDetail";
	}
	
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		return JSONUtil.getJSONString(1, e.toString());
	}
}
