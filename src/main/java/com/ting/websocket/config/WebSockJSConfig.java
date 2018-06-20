package com.ting.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ting.websocket.handler.MessageHandler;

@EnableWebSocket
@Configuration
public class WebSockJSConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	  registry.addHandler(new MessageHandler(),"/sockjs").setAllowedOrigins("*").withSockJS();
	}

}
