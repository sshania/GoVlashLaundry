package model;

import java.time.LocalDateTime;

public class Notification {
	private String notificationID;
	private String receipientID;
	private String notificationMessage;
	private LocalDateTime createdAt;
	private String isRead;
	
	public Notification(String notificationID, String receipientID, String notificationMessage, LocalDateTime createdAt,
			String isRead) {
		super();
		this.notificationID = notificationID;
		this.receipientID = receipientID;
		this.notificationMessage = notificationMessage;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}
	
	public String getNotificationID() {
		return notificationID;
	}
	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}
	public String getReceipientID() {
		return receipientID;
	}
	public void setReceipientID(String receipientID) {
		this.receipientID = receipientID;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	
}
