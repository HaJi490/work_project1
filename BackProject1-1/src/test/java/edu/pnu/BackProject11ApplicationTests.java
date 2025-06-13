package edu.pnu;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
class BackProject11ApplicationTests {

	@Autowired
	private MemberRepository mRepo;

	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	void contextLoads() {
		
		mRepo.save(Member.builder()
				.id("member@naver.com")
				.password(encoder.encode("1234"))
				.username("홍길동")
				.createDate(new Date())
				.enabled(true)
				.build());
		
	}

}
