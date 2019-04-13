package top.ourck.beans;

import java.util.Date;

public class LoginTicket {

	private int id;
	private int userId;
	private String ticket;
	private Date expired;
	private int status;

	public LoginTicket() {
		// TODO Auto-generated constructor stub
	}

	public LoginTicket(int id, int userId, String ticket, Date expired, int status) {
		super();
		this.id = id;
		this.userId = userId;
		this.ticket = ticket;
		this.expired = expired;
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoginTicket [id=" + id + ", userId=" + userId + ", ticket=" + ticket + ", expired=" + expired
				+ ", status=" + status + "]";
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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}