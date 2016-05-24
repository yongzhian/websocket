package websocketserver;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/websocket")
public class WebSocket {

	private Logger logger = Logger.getLogger(WebSocket.class);
	
	@OnMessage
	public void OnMessage(String message, Session session) {
		logger.info("received message : " + message);

	}
	
	@OnOpen
	public void OnOpen() {
		logger.info("websocket connected!");
	}
	
	@OnClose
	public void OnClose() {
		logger.info("websocket closed!");
	}
}
