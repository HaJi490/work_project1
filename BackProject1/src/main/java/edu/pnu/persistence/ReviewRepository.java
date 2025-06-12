package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.OrderReviews;

@Repository
public interface ReviewRepository extends JpaRepository<OrderReviews, String> {
	List<OrderReviews> findAllByOrderByReviewIdDesc(Pageable paging);
}
