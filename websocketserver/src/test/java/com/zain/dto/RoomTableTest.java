package com.zain.dto;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.zain.websocket.WebSocket;

public class RoomTableTest {
	private Logger logger = Logger.getLogger(RoomTableTest.class);
	@Test
	public void RoomTable(){
		long createTime = System.currentTimeMillis();
		Room room = new Room(Room.Type.P2P, "a", createTime); //默认创建p2p房间
		RoomTable.roomMap.put("a", room); 
		logger.info(RoomTable.roomMap);
		RoomTable.roomMap.remove("a");
		logger.info("移除后: " + RoomTable.roomMap);
	}
}
