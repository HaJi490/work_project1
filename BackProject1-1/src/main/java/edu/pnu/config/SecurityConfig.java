package edu.pnu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizaionFilter;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final AuthenticationConfiguration authConfig;
	private final MemberRepository memRepo;
	private final OAuth2SuccessHandler successHandler;
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{	// SecurityFilterChain을 등록하면 로그인을 강제하지 않는다
		http.csrf(csrf->csrf.disable());
		
		/// 작성한 필터(인가) 추가
		http.addFilterBefore(new JWTAuthorizaionFilter(memRepo), AuthorizationFilter.class);
		
		/// 권한설정(Authorizatin Filter)
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/**").permitAll() //api/public/**
				.anyRequest().authenticated());										// 다 permitAll이면 Authorization 필요xx ------------------------------!
		
		http.formLogin(frmLogin->frmLogin.disable());								//내가 ui까지 제공할때 사용----------------------------------------!
		
		http.httpBasic(basic-> basic.disable());
		
		/// 세션유지 하지않겠다
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		/// 작성한 필터(인증) 추가
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager()));	//인증 객체 생성
		
		/// oauth2 로그인
		http.oauth2Login(oauth2->oauth2.successHandler(successHandler));
		
		/// 접근 권한 없음 페이지 처리
		http.exceptionHandling(ex->ex.accessDeniedPage("/login"));
		
		/// 프론트 연결
		http.cors(cors->cors.configurationSource(corsSource()));
		
		/// 로그아웃은 프론트에서-------------------------!
		
		return http.build();
		
	}
	
	
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:5173");
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.setAllowCredentials(true);
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);	// 위 설정을 적용할 Rest서버의 URL 패턴설정
		return source;
	}
}
