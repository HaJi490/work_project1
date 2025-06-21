package edu.pnu.config.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import edu.pnu.persistence.MemberRepository;
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
	private final MemberRepository memRepo;	// di가 아니어서 SecurityConfig에서 매개변수로 넘겨줘야 사용가능
	
	@Override	// POST /login 요청 시 인증 메서드
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword());
			
			// 인증 진행 -> UserDetailService의 loadUserByUsername에서 DB 사용자 정보를 읽어온 뒤 요청정보와 비교
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
		
		Member member = memRepo.findById(user.getUsername()).get();
		
		// JWT를 생성 (username으로)
		String token = JWTUtil.getJWT(user.getUsername());
		
		// 응답 Json 생성
		Map<String, Object> resp = new HashMap<>();
		resp.put("role", member.getRole());
		resp.put("username", member.getUsername());
		
		response.setCharacterEncoding("UTF-8");
		
		ObjectMapper mapper = new ObjectMapper();
	    String responseJson = mapper.writeValueAsString(resp);
	    response.getWriter().write(responseJson);
//		response.getWriter().print(member.getRole());	// 응답이 하나일때만 가능
		
		// Response 헤더에 JWT를 저장해 응답
		response.addHeader(HttpHeaders.AUTHORIZATION, token);
		response.setStatus(HttpStatus.OK.value());
	}
}
