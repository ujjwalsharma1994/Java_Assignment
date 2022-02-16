package com.java.assignment.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.java.assignment.api.response.RestResponse;

@SuppressWarnings("serial")
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        final String expired = (String) request.getAttribute("expired");
        //System.out.println("expired @Kiran: "+expired);
        if (expired!=null){
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
        	RestResponse restResponse = new RestResponse(401, "Token expired, ", null, false);

        	PrintWriter out = response.getWriter();
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	out.print(restResponse);
        	response.getWriter().flush();
        	response.getWriter().close();
        	
        }else{
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
        }

	}
}