package com.zain.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomTable {
	// 最大通话数量
	public static final int MAX_COUNT = 20;
	public static final long MAX_TIME_OUT = 2 * 60 * 1000; //超时时间2分钟
	
	public static Map<String,Room> roomMap=new ConcurrentHashMap<String,Room>();
	
	private RoomTable(){}

	/**
	 * 注册房间
	 * @return
	 */
	public boolean registeRoom(Room room){
		return false;
	}
	
}
