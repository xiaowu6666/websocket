package com.ting.websocket.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	final Logger log = LoggerFactory.getLogger(MessageController.class);

	private SimpMessagingTemplate template;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	@MessageMapping("/hello")
	@SendTo("/queue/echo")
	public Map<String, Object> echo(String msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("message", msg);
		map.put("from", "server");
		map.put("now", new Date().getTime());
		log.info("receive msg from client: {}",msg);
		return map;
	}
	

	
	@MessageMapping("only")
	@SendToUser(value = "topic/myself",broadcast = false)
	public Map<String,Object> respMsg(String msg,Principal principal){
		log.info("recevie client msg : {} ,user : {}",msg,principal.getName());
		Map<String, Object> map = new HashMap<>();
		map.put("message", msg);
		map.put("now", new Date());
		map.put("from", "server");
		return map;
	}
}
