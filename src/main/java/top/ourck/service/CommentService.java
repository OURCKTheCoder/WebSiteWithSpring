package top.ourck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.ourck.beans.Comment;
import top.ourck.dao.CommentDAO;

@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;
	
	public void addComment(Comment comment) {
		commentDAO.addComment(comment);
	}
	
	public void deleteCommentById(int id) {
		commentDAO.deleteById(id);
	}
	
	public int getCommentCount(int entityId, int entityType) {
		return commentDAO.getCommentCountById(entityId, entityType);
	}
	
	public List<Comment> getChildComment(int entityId, int entityType) {
		return commentDAO.selectByEntity(entityId, entityType);
	}
}
