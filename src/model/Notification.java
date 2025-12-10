package model;

import java.time.LocalDateTime;

public class Notification {
	private String notificationID;
	private String recipientID;
	private String notificationMessage;
	private LocalDateTime createdAt;
	private boolean isRead;
	
	public Notification(String notificationID, String recipientID, String notificationMessage, LocalDateTime createdAt,
			boolean isRead) {
		super();
		this.notificationID = notificationID;
		this.recipientID = recipientID;
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
	public String getRecipientID() {
		return recipientID;
	}
	public void setRecipientID(String recipientID) {
		this.recipientID = recipientID;
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
	public boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
}
