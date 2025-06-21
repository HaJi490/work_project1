package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	@Autowired private MemberRepository memRepo;

	@Override	// JWTFilter에서 AuthManager의 authenticate메서드에서 실행
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memRepo.findById(id)	// 패스워드 틀려도 들어가지는건가---------? oo 근데 여기서 인증하는거 아니고 Filter에 authManager.authenticate()에서 인증
								.orElseThrow(()->new UsernameNotFoundException("Not Found!"));
		return User.builder().username(member.getId())
							.password(member.getPassword())
							.authorities(AuthorityUtils.createAuthorityList(member.getRole().toString()))
							.disabled(!member.isEnabled()).build();
	}

}
