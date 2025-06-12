package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.OrderPayments;

@Repository
public interface PaymentRepository extends JpaRepository<OrderPayments, String> {
	// 테스트
	List<OrderPayments> findAllByOrderByOrderIdDesc(Pageable paging);
	
//	String findBypayTypeContaing(String searchKeyword); // 그냥 keyword만 있으면 될듯..? 아니면 service에서 수정
}
