package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

//@SpringBootTest
public class InsertManagerTest {
	@Autowired private MemberRepository memRepo;
	@Autowired private PasswordEncoder encoder;
	
	//@Test
	public void insertManager() {
		memRepo.save(Member.builder()
							.id("manager")
							.password(encoder.encode("xpTmxm1234!"))
							.username("매니저")
							.role(Role.ROLE_MANAGER).build());
	}
	
	@Test
	public void insertMember() {
		memRepo.save(Member.builder()
							.id("member@naver.com")
							.password(encoder.encode("1111"))
							.username("멤버").build());
	}
}
