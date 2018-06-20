package com.ting.websocket.config;


import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.ting.websocket.handler.MessageHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MessageHandler(), "websocket")
		.addInterceptors(new HttpSessionHandshakeInterceptor())
		.setAllowedOrigins("*");
	}
	//这个是tomcat, WildFly, and GlassFish 
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxBinaryMessageBufferSize(1024*1024); //设置文字信息最大容量
		container.setMaxBinaryMessageBufferSize(5*1024*1024); //设置二进制文本信息大小
		return container;
	}
	
	/**
	 * For Jetty, you’ll need to supply a pre-configured Jetty WebSocketServerFactory
	 * and plug that into Spring’s DefaultHandshakeHandler through your WebSocket 
	 * 上面是spirng 官方的注解，不知道jetty为什么这么麻烦，要专门引入jetty socket 模块包
	 * @return
	 */
	@Bean
	public DefaultHandshakeHandler  handshakeHandler() {
		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setInputBufferSize(8892);
        policy.setIdleTimeout(600000);
        return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy))); 
	}
	
}
