package com.example.app.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Component
public class JwtFilter extends GenericFilterBean {
	
	
	@Autowired 
	private JwtUtil jwtUtil;
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	
    	
    	HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        	
        String path = req.getRequestURI();
        String method = req.getMethod();

        if (path.equals("/api/user/login") || path.equals("/api/user/register") || (path.startsWith("/api/products") && method.equals("GET"))) {
            chain.doFilter(request, response);
            return;
        }
        
        String authHeader = req.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(401);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);
            
            var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(email,null, List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role)));
           
//            	System.out.println("Setting authentication for: " + email);   //debug line
                SecurityContextHolder.getContext().setAuthentication(auth);
            
        } catch (Exception e) {
        	e.printStackTrace();
        	res.setStatus(401);
            res.getWriter().write("Invalid token" +e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }
}
