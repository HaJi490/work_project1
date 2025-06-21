package edu.pnu.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.CustomMyUtil;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component	// OAuth2로그인에 성공하면 실행 -> JWT토큰을 응답헤더에 추가하는 핸들러
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User user = (OAuth2User)authentication.getPrincipal();
		Map<String, String> userinfo = CustomMyUtil.getUsernameFromOAuth2User(user);
		if (userinfo == null) {
			throw new ServletException("Cannot generate username from oauth2user!");
		}
		String id = userinfo.get("id");
		String username = userinfo.get("username");
		
		// OAuth2 로그인 사용자에 대해 JWT를 이용한 검증목적 저장. 비밀번호는 실제 사용되지 않으므로 더미로 설정
		memRepo.save(Member.builder()
							.id(id)
							.password(encoder.encode("1234"))
							.username(username).build());
		String jwtToken = JWTUtil.getJWT(id);
		Cookie jwtCookie = new Cookie("jwtToken", URLEncoder.encode(jwtToken, "utf-8"));	
		
		jwtCookie.setHttpOnly(true);	// XSS 공격방지
		jwtCookie.setPath("/");			// 모든 경로에서 쿠키 사용가능
		jwtCookie.setSecure(true);		// https가 아니면 false
//		jwtCookie.setDomain("10.125.121.177");	// ------------------------?? 쿠키가 서브도메인 간 공유할 때 주로 쓰이고,IP기반 환경에서는 오히려 설정하지 않는 것이 낫기도 해.
		jwtCookie.setMaxAge(60*60); 		// 쿠키 유효시간(1시간)
		response.addCookie(jwtCookie);	// 쿠키에 토큰 저장
		
		// 프론트앤드 콜백 url 호출	// url 192.10으로 시작하면안됨, 배포하고싶으면 공짜 url받아서 하기
		response.sendRedirect("http://localhost:5173/");	//FIXME
	}
	
}
