package top.ourck.beans;

import java.util.Date;

public class Comment {
	
	private int id;
	private String content;
	private int status;
	private int entityType;
	private int entityId;
	private int userId;
	private Date createDate;
	
	public Comment() {}

	public Comment(int id, String content, int status, int entityType, int entityId, int userId, Date createDate) {
		super();
		this.id = id;
		this.content = content;
		this.status = status;
		this.entityType = entityType;
		this.entityId = entityId;
		this.userId = userId;
		this.createDate = createDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
