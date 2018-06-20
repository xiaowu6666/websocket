package com.ting.websocket.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MessageHandler extends TextWebSocketHandler{
	
	Logger log = LoggerFactory.getLogger(MessageHandler.class);
	
	//用来保存连接进来session
	private  List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	/**
	 * 关闭连接进入这个方法处理，将session从 list中删除
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	  sessions.remove(session);
	  log.info("{} 连接已经关闭，现从list中删除 ,状态信息{}", session, status);
	}

	/**
	 * 三次握手成功，进入这个方法处理，将session 加入list 中
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		log.info("用户{}连接成功.... ",session);
	}

	/**
	 * 处理客户发送的信息，将客户发送的信息转给其他用户
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("reveice client msg: {}",message.getPayload());
		session.sendMessage(new TextMessage("i reveice client msg...."+System.nanoTime()));
		for(WebSocketSession wss : sessions) 
			if(!wss.getId().equals(session.getId()))
				wss.sendMessage(message);
		
	}

	
}
