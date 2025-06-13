package edu.pnu.util;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomMyUtil {
	// OAuth2User 정보를 이용해서 임의의 사용자 아이디를 생성
	// 인증 서버를 추가하려면 이 메서드 수정
	@SuppressWarnings("unchecked")
	public static String getUsernameFromOAuth2User(OAuth2User user) {
		// 로그인 성공 후 인증서버에서 보내준 속성 정보 추출
		Map<String, Object> attmap = user.getAttributes();
		String userString = (String)user.toString();
		
		String ret = null;
		
		if(userString.contains("googleapis.com")) {
			ret = "Google_" + attmap.get("name") + "_" + attmap.get("sub");
		} else if(userString.contains("response=")) {
			Map<String, Object> map = (Map<String, Object>)attmap.get("response");
			ret = "Naver_" + map.get("name") + "_" + map.get("id");
		}
		
		// ret이 null이 아니면 ",", " " 제거
		if (ret != null) {
			ret = ret.replaceAll(",", "_").replaceAll(" ", "_");
		}
		return ret;
	}

}
