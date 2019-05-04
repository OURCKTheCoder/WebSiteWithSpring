package top.ourck.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.ourck.beans.Message;
import top.ourck.dao.MessageDAO;
import top.ourck.util.JSONUtil;

@Controller
@RequestMapping("/msg")
public class MessageController {

	@Autowired
	MessageDAO messageDAO;
	
	@RequestMapping("/add")
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
		messageDAO.addMessage(msg);
		
		return JSONUtil.getJSONString(0, msg.getId() + "");
	}
	
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception e) {
		return JSONUtil.getJSONString(1, e.toString());
	}
}
