package com.zain.controler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zain.dto.RoomTable;
import com.zain.util.StringTools;


@Controller
@RequestMapping({ "/rtc" })
public class WebrtcControler {
	Logger logger = Logger.getLogger(WebrtcControler.class);
	
	@Autowired
	public HttpSession session;
	
	@RequestMapping(value = "/req.do", method = RequestMethod.GET)
	public String roomCreate(HttpServletRequest req,HttpServletResponse resp) {
		String rid = req.getParameter("rid");
		String uid = req.getParameter("uid");
		
		if(StringUtils.isNotBlank(rid)){ //第一个加入
			req.setAttribute("initiator", "false");
			req.setAttribute("rid", rid);
			req.setAttribute("uid", StringTools.genRandomStr(5));
		}else{
			if(RoomTable.roomMap.size() >= RoomTable.MAX_COUNT){
				try {
					resp.getWriter().println("已经达到最大房间数!");
				} catch (IOException e) {
					logger.error("已经达到最大房间数", e);
				}
				return null;
			}
			
			req.setAttribute("initiator", "true");
			req.setAttribute("rid", StringTools.genRandomStr(5));
			req.setAttribute("uid", StringTools.genRandomStr(5));
		}
		
		return "rtcreq";
	}
	
	@RequestMapping(value = "/status.do", method = RequestMethod.GET)
	@ResponseBody
	public String status(HttpServletResponse response) {
		logger.debug("rtc status query.. ");
		return RoomTable.roomMap.toString();
	}
}
