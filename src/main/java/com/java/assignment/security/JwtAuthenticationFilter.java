package com.java.assignment.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.java.assignment.repository.UsersRepository;
import com.java.assignment.model.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UsersRepository userBasicInformationRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(Constants.HEADER_STRING);
		String username = null;
		String authToken = null;

		try {
			if (header != null && header.startsWith(Constants.TOKEN_PREFIX)) {
				authToken = header.replace(Constants.TOKEN_PREFIX, "");
				try {
					username = jwtTokenUtil.getUsernameFromToken(authToken);
	
				} catch (IllegalArgumentException e) {
					System.out.println("an error occured during getting username from token"+ e);
				} catch (ExpiredJwtException e) {
					System.out.println("the token is expired and not valid anymore "+ e);
					
					req.setAttribute("expired",e.getMessage());
					authenticationEntryPoint.commence(req, res, null);
	
				} catch (SignatureException e) {
					System.out.println("Authentication Failed. Username or Password not valid.");
				}
				catch (Exception e) {
					System.out.println("Authentication Failed. Username or Password not valid.");
				}
			} else {
				
				System.out.println("couldn't find bearer string, will ignore the header");
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	
				Users userDetail = userBasicInformationRepository.findFirstUsersByUsername(username);

				if (jwtTokenUtil.validateToken(authToken, userDetail)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetail, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} 
		} catch(Exception e) {
			SecurityContextHolder.clearContext();
		}
		chain.doFilter(req, res);
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().match("/users/create", request.getServletPath());
	}
}