package com.zain.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zain.dto.Client;
import com.zain.dto.Room;
import com.zain.dto.RoomTable;

@ServerEndpoint(value = "/rtc/{rid}/{uid}")
public class WebSocket {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	private Logger logger = Logger.getLogger(WebSocket.class);

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("received message : " + message);
		try {
			session.getBasicRemote().sendText("欢迎你 上线");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param session
	 * @param rid
	 * @param uid
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("rid") String rid, @PathParam("uid") String uid) {
		long createTime = System.currentTimeMillis();
		Room room = null;
		logger.info("客户端连接建立 ,rid : " + rid + "  uid:"+ uid);
		if (RoomTable.roomMap.containsKey(rid)) { // 当前房间已经存在
			room = RoomTable.roomMap.get(rid);
			if (room.addClient(new Client(uid, new CopyOnWriteArrayList<String>(), createTime, session))) {
				logger.info("客户端加入房间成功 ,rid : " + rid + "  uid:"+ uid);
				return;
			}else{
				logger.info("客户端加入房间失败 ,rid : " + rid + "  uid:"+ uid);
			}
		} else {
			room = new Room(Room.Type.P2P, rid, createTime); //默认创建p2p房间
			if (room.addClient(new Client(uid, new CopyOnWriteArrayList<String>(), createTime, session))) {
				logger.info("客户端加入房间成功 ,rid : " + rid + "  uid:"+ uid);
				RoomTable.roomMap.put(rid, room); //第一次需要注册房间到RoomTable
				return;
			}else{
				logger.info("客户端加入房间失败 ,rid : " + rid + "  uid:"+ uid);
			}
		}
		
		try {
			session.close(new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY, "客户端加入房间失败"));
		} catch (IOException e) {
			logger.error("连接异常，关闭连接", e);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("rid") String rid, @PathParam("uid") String uid) {
		logger.info("客户端连接断开 ,rid : " + rid + "  uid:"+ uid);
		if (RoomTable.roomMap.containsKey(rid)) { // 当前房间已经存在
			Room room = RoomTable.roomMap.get(rid);
			if(room.removeClient(session,uid)){
				logger.info("移除客户端成功 ,rid : " + rid + "  uid:"+ uid);
				if(room.getClientsMap().isEmpty()){
					RoomTable.roomMap.remove(rid);//没有用户时则清空
					logger.info("房间用户清空，移除房间.");
				}
				return;
			}
		}
		logger.info("移除客户端失败，无此房间或客户端 ,rid : " + rid + "  uid:"+ uid);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable){
		
	}

	public static synchronized void addOnlineCount() {
		onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		onlineCount--;
	}
}
