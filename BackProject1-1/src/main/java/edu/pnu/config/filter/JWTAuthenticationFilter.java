package edu.pnu.config.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authManager; // 인증객체
	
	@Override	// POST /login 요청 시 인증 메서드
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Member member = mapper.readValue(request.getInputStream(), Member.class);	//
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword());
			
			// 인증 진행 -> UserDetailService의 loadUserByUsername에서 DB 사용자 정보를 읽어온 뒤 요청정보와 비교----------------------------------만들어야함
			return authManager.authenticate(authToken);
		} catch (Exception e) {
			log.info(e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return null;
	}
	
	@Override	// 자격증명 성공 시 
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// 자격 증명이 성공하면 loadUserByUsername에서 만든 객체가 authResult에 담겨있음
		User user = (User)authResult.getPrincipal();
		System.out.println("auth: " + user);
		
		// username으로 JWT를 생성
		String token = JWTUtil.getJWT(user.getUsername());
		
		// Response Header에 JWT를 저장해 응답
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}
}
