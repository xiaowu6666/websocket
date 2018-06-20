package com.ting.websocket.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
public class WebSocketSecurtiyConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	 @Override
	    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
	        messages 
	                //    任何人都可以订阅/ user / queue / errors
	                .simpSubscribeDestMatchers("/user/queue/errors").permitAll() 
	                 //何具有以“/ app /”开头的目标邮件都将要求用户具有角色ROLE_USER
	                .simpDestMatchers("/app/**").hasRole("USER").anyMessage().authenticated(); 
	    }
	
	    //允许跨域
	    @Override
	    protected boolean sameOriginDisabled() {
	        return true;
	    }
	    
	    
}
