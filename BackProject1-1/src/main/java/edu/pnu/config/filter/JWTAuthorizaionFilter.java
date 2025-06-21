package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// Role없으니까 필요없?-------------------------!필요함. 멤버도 권한
public class JWTAuthorizaionFilter extends OncePerRequestFilter{	 // OncePerRequestFilter: 하나의 요청에 대해서 단 한번만 필터를 거침
	private final MemberRepository memRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String srcToken = request.getHeader(HttpHeaders.AUTHORIZATION);	// 요청헤더의 Authorization을 가져옴
		/// srcToken이 없거나 "Bearer "로 시작하지 않으면 리턴(필터 그냥통과)
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {		
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwtToken = srcToken.replaceAll("Bearer ", "");
		
		
		String username = JWTUtil.getClaim(jwtToken);					// 토큰에서 username 추출
		
		Optional<Member> opt = memRepo.findById(username);				// 토큰의 username으로 DB검색해서 사용자 검색
		/// DB에 해당 사용자가 없다면 리턴(필터 그냥통과)
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Member findmember = opt.get();
		User user = new User(findmember.getId(), findmember.getPassword(), AuthorityUtils.createAuthorityList(findmember.getRole().toString())); /// 변경완료> 권한(ROLE)설정시 변경
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());	// null: 비밀번호, 이미 인증 완료되서 비워둠
		SecurityContextHolder.getContext().setAuthentication(auth); 	// 시큐리티 세션에 등록
		
		filterChain.doFilter(request, response);
		
	}

}
