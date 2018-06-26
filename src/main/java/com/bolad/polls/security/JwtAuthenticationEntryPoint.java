package com.bolad.polls.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
 * It implements AuthenticationEntryPoint interface and provides the implementation 
 * for its commence() method. This method is called whenever an exception is thrown due 
 * to an unauthenticated user trying to access a resource that requires authentication.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest, 
						 HttpServletResponse httpServletResponse,
			org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
		logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, 
				"Sorry you're not authorized to access this resource.");	
	}	

}
