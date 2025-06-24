package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class InsertDummieMember {
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	@Test
	public void insertDummies() {
		List<Member> dummyMembers = List.of(
			    Member.builder().id("user1").password(encoder.encode("xpTmxm1234!")).username("김지은").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user2").password(encoder.encode("xpTmxm1234!")).username("이민수").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user3").password(encoder.encode("xpTmxm1234!")).username("최윤아").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user4").password(encoder.encode("xpTmxm1234!")).username("박성우").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user5").password(encoder.encode("xpTmxm1234!")).username("정하늘").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user6").password(encoder.encode("xpTmxm1234!")).username("오세진").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user7").password(encoder.encode("xpTmxm1234!")).username("윤도현").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user8").password(encoder.encode("xpTmxm1234!")).username("한소영").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user9").password(encoder.encode("xpTmxm1234!")).username("백지훈").role(Role.ROLE_MEMBER).build(),
			    Member.builder().id("user10").password(encoder.encode("xpTmxm1234!")).username("임서연").role(Role.ROLE_MEMBER).build()
			);
		
		for(Member mem: dummyMembers) {
			memRepo.save(mem);
		}
	}
}
