package edu.pnu.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component	//JWT토큰을 응답헤더에 추가하는 핸들러
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User user = (OAuth2User)authentication.getPrincipal();
		String id = CustomMyUtil.getUsernameFromOAuth2User(user);
		if (id == null) {
			throw new ServletException("Cannot generate username from oauth2user!");
		}
		
		// OAuth2 로그인 사용자에 대해 JWT를 이용한 검증목적 저장. 비밀번호는 실제 사용되지 않으므로 더미로 설정
		memRepo.save(Member.builder()
							.id(id)
							.password(encoder.encode("1234"))
							.username("가나다").build());
		String jwtToken = JWTUtil.getJWT(id);
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
	}
	
}
