package com.java.assignment.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class CORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;

		@SuppressWarnings("unused")
		String header_authorization = httpServletRequest.getHeader("Authorization");
		
		//System.out.println("Filtering on........................."+header_authorization+"..................................");
		
		
		HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
        chain.doFilter(req, res);
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> filter() {
		FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>();

		bean.setFilter(new JwtAuthenticationFilter());

		return bean;
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}