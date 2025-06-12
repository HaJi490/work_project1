package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// 1. domain에 Orders, payments, reviews 생성
	// 2. 각 Repositroy 생성
	// 3. Olist Service, Controller에서 정보 보내기
}
