package edu.pnu.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {
	public static final String JWT_KEY = "edu.pnu.jwt";
	private static final long ACCESS_TOKEN_MSEC = 100 * ( 60 * 1000); //토큰의 유효시간(100분)
	private static final String claimName = "usename";
	private static final String prefix = "Bearer "; 
	
	private static String getJWTSource(String token) {	// 헤더에 "Bearer "제거
		if(token.startsWith(prefix)) return token.replace(prefix, "");
		return token;
	}
	
	public static String getJWT(String username) {		// JWT 토큰생성
		String src = JWT.create()
						.withClaim(claimName, username)
						.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MSEC))
						.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix + src;
	}
	
	public static String getClaim(String token) {		// 토큰에 담긴 "username" 추출
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getClaim(claimName).asString();
	}
	
	public static boolean isExpired(String token) {		// 만료여부 검사
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getExpiresAt().before(new Date());
	}
	
}
