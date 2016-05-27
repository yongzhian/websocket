package com.zain.controler;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zain.dto.RoomTable;


@Controller
@RequestMapping({ "/rtc" })
public class WebrtcControler {
	Logger logger = Logger.getLogger(WebrtcControler.class);
	
	@Autowired
	public HttpSession session;
	
	@RequestMapping(value = "/status.do", method = RequestMethod.GET)
	@ResponseBody
	public String status(HttpServletResponse response) {
		logger.debug("rtc status query.. ");
		return RoomTable.roomMap.toString();
	}
}
