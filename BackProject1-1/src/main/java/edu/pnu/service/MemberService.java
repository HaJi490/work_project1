package edu.pnu.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	// 회원가입
	public void save(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
		member.setEnabled(true); 
		if(member.getCreateDate() == null) member.setCreateDate(new Date());
		
		memRepo.save(member);
	}
	
	public Boolean existById(String id) {
		Boolean check = memRepo.existsById(id);
		return check;
	}
}
