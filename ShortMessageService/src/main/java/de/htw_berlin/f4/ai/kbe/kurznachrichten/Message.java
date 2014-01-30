package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Date;

public class Message {

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Boolean isOrigin() {
		return origin;
	}

	public void setOrigin(Boolean origin) {
		this.origin = origin;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	// creationDate
	private Date date;

	private String content;

	private String topic;

	private User user;

	private Boolean origin;

	private Long messageId;

}
