package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.InquiryStatusHistory;
import edu.pnu.domain.InquiryStatusHistoryDto;

public interface InquiryStatusRepository extends JpaRepository<InquiryStatusHistory, Long> {
	@Query("SELECT status, comment, modifiedAt FROM InquiryStatusHistory WHERE inquiryId = :inquiryId")
	List<InquiryStatusHistoryDto> findStatusHistoryById(@Param("inquiryId") Long id);
}
