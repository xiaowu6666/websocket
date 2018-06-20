package com.ting.websocket.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
	        .antMatchers("/stomp").access("hasRole('USER')")
	        .and().formLogin().loginPage("/login").successHandler(new SimpleUrlAuthenticationSuccessHandler("/stomp"))
	        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
	        .invalidateHttpSession(true).deleteCookies("SESSIONID")
	        //记住我功能
	        .and().rememberMe().tokenValiditySeconds(10);
	        ;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		     .withUser("张三").password("123456").roles("USER").and()
		     .withUser("李四").password("123456").roles("USER","ADMIN");
	}
}
