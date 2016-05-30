package com.zain.ws;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;

public class MyClientApp {
	@Test
	public void connTest(){
		WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		String uri ="ws://localhost:11587/websocketserver/rtc/a/a"; 
		System.out.println("Connecting to"+ uri); 
		 try { 
			 Session  session = container.connectToServer(WsClient.class, URI.create(uri)); 
			 session.getBasicRemote().sendText("wos client");
			 session.close();
		 } catch (DeploymentException e) { 
		e.printStackTrace();
		 } catch (IOException e) { 
		e.printStackTrace();
		}
	}
}
