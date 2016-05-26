package com.zain.dto;

import java.util.List;

import javax.websocket.Session;

public class Client {

	private String id;
	private List<String> msgs;
	private long createTime;
	private Session session;

	public Client() {
		super();
	}

	public Client(String id, List<String> msgs, long createTime, Session session) {
		super();
		this.id = id;
		this.msgs = msgs;
		this.createTime = createTime;
		this.session = session;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [userName=");
		builder.append(id);
		builder.append(", msgs=");
		builder.append(msgs);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", session=");
		builder.append(session);
		builder.append("]");
		return builder.toString();
	}

}
