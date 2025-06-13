package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {
	@Autowired MemberService memserv;
	
	@GetMapping("/auth/signin")
	public void join() {}
	
	@PostMapping("/auth/signin")
	public void joinProc(@RequestBody Member member) {
		memserv.save(member);
	}
	
	// id중복확인
	@PostMapping("/duplId")
	public void checkDupId(@RequestBody String id) {
		Boolean check = memserv.existById(id);
		if(check) {} //---------------------------!
	}
}
