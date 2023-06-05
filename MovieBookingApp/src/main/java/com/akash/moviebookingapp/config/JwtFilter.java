package com.akash.moviebookingapp.config;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import com.akash.moviebookingapp.exception.InvalidAuthorizationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends GenericFilterBean {
	
//	@Value("${application.jwt.secret-key}")
	private String secretKey="76397924423F4528482B4D6251655468576D5A7134743777217A25432A46294A";


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		try {
			if(req.getMethod().equals("OPTIONS")) {
				//SKIP jwt authentication
//				System.out.println("skipped jwt authentication for preflight request");
				chain.doFilter(request, response);
			}
			else {
				String authHeader = req.getHeader("Authorization");
//				System.out.println(authHeader);
				if(authHeader==null || !authHeader.startsWith("Bearer")) {
					throw new ServletException("missing or invalid authorization header");
				}	
				final String jwtToken = authHeader.substring(7);
				byte[] keyBytes = Decoders.BASE64.decode(secretKey);
				Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build().parseClaimsJws(jwtToken).getBody();
				req.setAttribute("username", claims);
				
				chain.doFilter(request, response);			
			}		
		} catch(ServletException | ExpiredJwtException ex) {
			res.setStatus(401);
			System.out.println(ex.getMessage());
		} 
		catch(Exception ex) {
			res.setStatus(500);
			System.out.println(ex.getMessage());
		}
		
	}

}
