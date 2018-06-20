package com.ting.websocket.mvc.config;


import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;



public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext ctx) throws ServletException {
		// TODO Auto-generated method stub
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebAppServletConfig.class);
		//注册websocket
		//context.register(WebSocketConfig.class);
		//context.register(WebSockJSConfig.class);
	//	context.register(WebSocketStompConfig.class);
		context.setServletContext(ctx);
		ServletRegistration.Dynamic dynamic= ctx.addServlet("dispatcher", new DispatcherServlet(context));
		dynamic.setLoadOnStartup(1);
		dynamic.addMapping("/");
		Dynamic filter = ctx.addFilter("characterEncodingFilter", new CharacterEncodingFilter("utf-8"));
		filter.addMappingForUrlPatterns(null, true, "/");
	}

}
