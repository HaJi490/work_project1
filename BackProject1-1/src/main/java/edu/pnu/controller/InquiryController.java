package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Inquiry;
import edu.pnu.domain.InquiryDetailDto;
import edu.pnu.domain.InquiryListDto;
import edu.pnu.domain.InquiryStatus;
import edu.pnu.domain.InquiryStatusUpdateRequest;
import edu.pnu.domain.Member;
import edu.pnu.service.InquiryService;

@RestController
@RequestMapping("/api") // 이 컨트롤러의 경로앞에 모두 추가
public class InquiryController {
	@Autowired private InquiryService inquiryserv;
	
	// Controller에 도달했을 때 이미 인증이 끝난 상태이고, @AuthenticationPrincipal은 그 인증된 사용자 객체를 꺼내는 편한 방법
	/// Member
	// 분석요청
	@PostMapping("/member/inquiry")
	public void saveInquiry(@RequestBody Inquiry inquiry,			// json -> dto객체
							@AuthenticationPrincipal User user) {	// 해당 유저정보
		String memId = user.getUsername();	// 멤버엔티티의 id = SecurityUserDetailService에서 넣은 id
		
		Member member = new Member();
		member.setId(memId);
		
		inquiry.setMember(member);
		inquiryserv.saveInquiry(inquiry);
	}
	
	// 내가 요청한 목록조회
	@GetMapping("/member/inquiry")
	public ResponseEntity<?> getMyList(@AuthenticationPrincipal User user) {
		String memId = user.getUsername();
		List<InquiryListDto> dto = inquiryserv.getMyList(memId);
		if(dto.isEmpty()) return ResponseEntity.noContent().build();	//204에러
		return ResponseEntity.ok(dto);
	}
	
	// 내 분석 상세보기
	@GetMapping("/member/inquiry/{id}")
	public ResponseEntity<?> getMyInquiryDetail(@PathVariable long id, @AuthenticationPrincipal User user) {	// inquiryId
		String memId = user.getUsername();
		InquiryDetailDto dto = inquiryserv.getMyInquiryDetail(id, memId);
		if(dto == null) return ResponseEntity.status(500).body("접근권한이 없습니다.");
		return ResponseEntity.ok(dto);
	}
	
	/// Manager
	// 전체분석 요청 목록
	@GetMapping("/manager/inquiry")
	public List<InquiryListDto> getAllList() {
		return inquiryserv.getAllList();
	}
	
	// 특정 분석 상세보기
	@GetMapping("/manager/inquiry/{id}")
	public InquiryDetailDto getInquiryDetail(@PathVariable long id) {// inquiryId
		return inquiryserv.getInquiryDetail(id);
	}
	
	// 상태목록 보내기
	@GetMapping("/inquiry/status-list")
	public List<String> getStatusList(){
		List<String> list = new ArrayList<>();
		for(InquiryStatus s : InquiryStatus.values()) {
			list.add(s.name());	// enum상수명을 문자열로 바꿈
		}
		return list;
	}
	
	// 분석 상태, 상담 내용 업데이트
	@PostMapping("/manager/inquiry/{id}/status")	// 경로충돌방지, 가독성, RESTful 설계원칙 -> inquiryId는 따로 받기
	public void updateStatus(@PathVariable long id, @RequestBody InquiryStatusUpdateRequest history) {
		inquiryserv.updateStatus(id, history);
	}
}
