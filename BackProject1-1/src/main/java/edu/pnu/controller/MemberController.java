package edu.pnu.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.IdCheckResponse;
import edu.pnu.domain.Member;
import edu.pnu.domain.MemberInfoDto;
import edu.pnu.domain.MemberUpdateRequest;
import edu.pnu.domain.MemberUpdateResult;
import edu.pnu.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MemberController {
	@Autowired MemberService memserv;
	
	@PostMapping("/api/jwtcallback")
	public ResponseEntity<?> jwtCallback(HttpServletRequest request){
		String jwtToken = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(!cookie.getName().equalsIgnoreCase("jwtToken")) continue;
			try {
				jwtToken = URLDecoder.decode(cookie.getValue(), "utf-8");	// 쿠키에 저장된 토큰은 인코딩되어 있으므로 디코딩해서 저장
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		}
		// 응답헤더(Authorizaion)에 JWT 저장해서 응답
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken).build();
 	}
	
	
	@PostMapping("/auth/signup")
	public void joinProc(@RequestBody Member member) {	// RequestBody dto객체로 받음
		memserv.save(member);
	}
	
	// id 중복확인
	@PostMapping("/idcheck")
	public IdCheckResponse existById(@RequestParam String id) {
		IdCheckResponse resp = new IdCheckResponse();
		if(memserv.existById(id)) {
			resp.setAvailable(false);
			resp.setMessage("이미 사용 중인 아이디입니다.");
		} else {
			resp.setAvailable(true);
			resp.setMessage("사용 가능한 아이디입니다.");
		}
		return resp;
	}
	
	// 회원정보 보내기
	@GetMapping("/api/member/info")
	public MemberInfoDto getMemberinfo(@AuthenticationPrincipal User user) {
		String memId = user.getUsername();
		MemberInfoDto dto = memserv.getMemberinfo(memId);
		return dto;
	}
	
	// 회원정보 수정 ----------------dto로 받기, ResponseEntity
	@PostMapping("/api/member/update")
	public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest member,
							@AuthenticationPrincipal User user) {
		String memId = user.getUsername();
		MemberUpdateResult result = memserv.updateMember(memId, member);

	    switch (result) {
	        case SUCCESS:
	            return ResponseEntity.ok("회원정보가 성공적으로 수정되었습니다.");
	        case NOT_FOUND:
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
	        case PASSWORD_MISMATCH:
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 일치하지 않습니다.");
	        default:
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
	    }
	}
	
	// 회원탈퇴 ----------------ResponseEntity
	@PostMapping("/api/member/delete")
	public boolean deleteMember(@AuthenticationPrincipal User user) {
		String memId = user.getUsername();
		if(!memserv.deleteMember(memId)) return false;
		return true;
	}
	
	
	
}
