package top.ourck.beans;

import java.util.Date;

public class News {

	@Override
	public String toString() {
		return "News [id=" + id + ", userId=" + userId + ", createdDate=" + createdDate + ", commentCount="
				+ commentCount + ", image=" + image + ", link=" + link + ", title=" + title + ", likeCount=" + likeCount
				+ "]";
	}

	private int id;
	private int userId;
	private Date createdDate;
	private int commentCount;
	private String image;
	private String link;
	private String title;
	private int likeCount;
	
	public News(int id, int userId, Date createdDate, int commentCount, String image, String link, String title) {
		super();
		this.id = id;
		this.userId = userId;
		this.createdDate = createdDate;
		this.commentCount = commentCount;
		this.image = image;
		this.link = link;
		this.title = title;
	}

	public News() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
