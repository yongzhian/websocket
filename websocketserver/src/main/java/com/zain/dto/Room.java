package com.zain.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.apache.log4j.Logger;

public class Room {
	private Logger logger = Logger.getLogger(Room.class);
	public enum Type {
		P2P, //p2p适合2个用户
		MULT //适合多对多
	}

	private Type type;
	private String roomId;
	public  Map<String,Client> clientsMap = new ConcurrentHashMap<String,Client>();
	private long createTime;
	

	public Room() {
		super();
	}

	public Room(Type type, String roomId,  long createTime) {
		super();
		this.type = type;
		this.roomId = roomId;
		this.createTime = createTime;
	}

	/**
	 * 添加用户到房间中
	 * @param client
	 * @return
	 */
	public boolean addClient(Client client){
		if(null != client && !this.clientsMap.containsKey(client.getId())){
			if(this.type.equals(Type.P2P) && this.clientsMap.size()<2){ //p2p房间最多允许2个用户
				this.clientsMap.put(client.getId(), client);
			}else{ //多人房间可任意进入
				this.clientsMap.put(client.getId(), client);
			}
			return true;
		}else{
			logger.error("添加客户端失败,client:" +client);
			return false;
		}
	}
	
	/**
	 * 删除用户
	 * @param client
	 * @return
	 */
	public boolean removeClient(Session session,String clientId){
		Client client  = this.getClientsMap().get(clientId);//通过session
		if(null != client && null != client.getSession() &&  client.getSession().getId().equals(session.getId())){
			return this.clientsMap.remove(clientId) == null;
		}
		return false;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Map<String, Client> getClientsMap() {
		return clientsMap;
	}

	public void setClientsMap(Map<String, Client> clientsMap) {
		this.clientsMap = clientsMap;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Room [type=");
		builder.append(type);
		builder.append(", roomId=");
		builder.append(roomId);
		builder.append(", clientsMap=");
		builder.append(clientsMap);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}


}
