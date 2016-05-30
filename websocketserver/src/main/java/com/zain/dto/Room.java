package com.zain.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Room {
	private Logger logger = Logger.getLogger(Room.class);

	public enum Type {
		P2P, // p2p适合2个用户
		MULT // 适合多对多
	}

	private Type type;
	private String roomId;
	public Map<String, Client> clientsMap = new ConcurrentHashMap<String, Client>();
	private long createTime;

	public Room() {
		super();
	}

	public Room(Type type, String roomId, long createTime) {
		super();
		this.type = type;
		this.roomId = roomId;
		this.createTime = createTime;
	}

	/**
	 * 添加用户到房间中
	 * 
	 * @param client
	 * @return
	 */
	public boolean addClient(Client client) {
		if (null != client && !this.clientsMap.containsKey(client.getId())) {
			if (this.type.equals(Type.P2P) && this.clientsMap.size() < 2) { // p2p房间最多允许2个用户
				this.clientsMap.put(client.getId(), client);
				final Session session  = client.getSession();
				if (this.clientsMap.size() == 1) { // 如果房间只有一个客户端 则超时清除
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							logger.info("------if other client doesn't join room ,destory the first client after a fix time--------");
							if (clientsMap.size() == 1) {
								try {
//									RoomTable.timeOutClient.add(client.getId());
									if(session.isOpen()){
										session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "超时仍无用户加入.."));
									}else{
										logger.info("session already closed.");
									}
								} catch (IOException e) {
									 logger.error("超时清除ws连接异常", e);
								}
							}
							this.cancel(); // 执行一次后销毁
						}
					}, RoomTable.MAX_TIME_OUT);// 设定指定的时间time,此处为2000毫秒
				}
			} else { // 多人房间可任意进入
				this.clientsMap.put(client.getId(), client);
			}
			return true;
		} else {
			logger.error("添加客户端失败,client:" + client);
			return false;
		}
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param clientId
	 * @return
	 */
	public Client getCurrClient(String clientId) {
		if (this.clientsMap.containsKey(clientId)) {
			return this.clientsMap.get(clientId);
		}
		return null;
	}

	/**
	 * 获取其他用户信息
	 * 
	 * @param clientId
	 * @return
	 */
	public List<Client> getOtherClient(String clientId) {
		List<Client> clientList = new ArrayList<Client>();
		if (StringUtils.isNotBlank(clientId) && this.clientsMap != null && this.clientsMap.size() > 0) {
			if (!this.clientsMap.containsKey(clientId)) {
				logger.info("clientid 不存在,clientMap : " + this.clientsMap + "  要查询的ID clientId: " + clientId);
				return null;
			}

			for (String _clientId : this.clientsMap.keySet()) {
				if (!clientId.equals(_clientId)) {
					clientList.add(this.clientsMap.get(_clientId));
				}
			}
			return clientList;
		}

		return null;

	}

	/**
	 * 删除用户
	 * 
	 * @param client
	 * @return
	 */
	public boolean removeClient(Session session, String clientId) {
		Client client = this.getClientsMap().get(clientId);// 通过session
		if (null != client) {
			if (null != client.getSession() && client.getSession().getId().equals(session.getId())) {
				return this.clientsMap.remove(clientId) != null;
			}
			if (RoomTable.timeOutClient.contains(clientId)) {
				return this.clientsMap.remove(clientId) != null;
			}
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
