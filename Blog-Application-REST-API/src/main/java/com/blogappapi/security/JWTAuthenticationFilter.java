package com.blogappapi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenHelper jwtHelper;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService; // it has loadloadUserByUsername method
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		

		// Authorization

		String requestToken = request.getHeader("Authorization");
		// Bearer 2352345235sdfrsfgsdfsdf
		
		//logger.info(" Header :  {}", authHeader);
		
		System.out.println(requestToken);
		
		
		String username = null;
		
		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			
			// looking good
			token = requestToken.substring(7);
			
			
			try {
				username = this.jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				//logger.info("Illegal Argument while fetching the username !!");
				System.out.println("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				//logger.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				//logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} 

		}

	
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user detail from username
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			
			//Boolean validateToken = ;
			
			if (this.jwtHelper.validateToken(token, userDetails)) {

				// set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				logger.info("Validation fails !!");
				System.out.println("Username is null or Context is not null !!");
			}

		}

		filterChain.doFilter(request, response);

		}
		
	

}