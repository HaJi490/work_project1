package edu.pnu.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.MemberInfoDto;
import edu.pnu.domain.MemberUpdateRequest;
import edu.pnu.domain.MemberUpdateResult;
import edu.pnu.domain.Role;
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
		if(member.getRole() == null) member.setRole(Role.ROLE_MEMBER);
		
		memRepo.save(member);
	}
	
	// id 중복확인
	public Boolean existById(String id) {
		Boolean check = memRepo.existsById(id);
		return check;
	}
	
	// 회원정보 보내기
	public MemberInfoDto getMemberinfo(String memId) {
		Optional<Member> opt = memRepo.findById(memId);
		if(opt == null) return null;
		Member dbmem = opt.get();
		MemberInfoDto dto = new MemberInfoDto();
		dto.setId(dbmem.getId());
		dto.setUsername(dbmem.getUsername());
		return dto;
	}
	
	// 회원정보 수정
	public MemberUpdateResult updateMember(String id, MemberUpdateRequest mem) {
		Optional<Member> opt = memRepo.findById(id);
		if (opt.isEmpty()) return MemberUpdateResult.NOT_FOUND;
		
		Member dbmem = opt.get();
		if(mem.getUsername() != null && !mem.getUsername().trim().isEmpty()) {
			dbmem.setUsername(mem.getUsername());
		}
		
		// 새 비밀번호바꾸기
		if(mem.getNewpwd() != null && !mem.getNewpwd().trim().isEmpty()) { 
			// 현재 비밀번호 확인
			if( mem.getCurrentpwd() == null || !encoder.matches(mem.getCurrentpwd(), dbmem.getPassword())) {
				return MemberUpdateResult.PASSWORD_MISMATCH;
			}
		
			dbmem.setPassword(encoder.encode(mem.getNewpwd()));
		}
		
		memRepo.save(dbmem);
		return MemberUpdateResult.SUCCESS;
	}
	
	// 회원 탈퇴
	public boolean deleteMember(String id) {
		Optional<Member> opt = memRepo.findById(id);
		if(opt.isEmpty()) return false;
		Member dbmem = opt.get();
		dbmem.setEnabled(false);
		memRepo.save(dbmem);
		return true;
	}
}
