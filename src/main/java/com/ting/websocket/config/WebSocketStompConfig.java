package com.ting.websocket.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


//这个配置类不仅配置了 WebSocket，还配置了基于代理的 STOMP 消息；
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.ting.websocket.controller")
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 这个服务器并不是用ws:// 而是用http:// 或者 https:// 来连接
		registry.addEndpoint("endpoint").setAllowedOrigins("*")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 定义了两个客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息
		registry.enableSimpleBroker("/topic", "/queue");
		// 定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀 
		registry.setApplicationDestinationPrefixes("/app");
		//使用客户端一对一通信的时候 编配前缀 通常与@SendToUser 搭配使用
		registry.setUserDestinationPrefix("/user"); 

	}


	
	
	


	

}
