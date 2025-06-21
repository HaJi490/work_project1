package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.Inquiry;
import edu.pnu.domain.InquiryDetailDto;
import edu.pnu.domain.InquiryListDto;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>{
    // 리스트 목록별로
	@Query("SELECT i.id, i.title, i.organization, i.inquiryDate, i.status " + 
            "FROM Inquiry i WHERE i.member.id = :memberId")
     List<InquiryListDto> findAllByMemberId(@Param("memberId") String memberId);
    
    // 매니저 상세보기_member빼고 들고옴
    @Query("SELECT id, name, organization, email, phone, title, content, status, inquiryDate " +
    		"FROM Inquiry WHERE id = :id")
    InquiryDetailDto findInquiryById(@Param("id") Long id);
    
    // 멤버 상세보기_meberId가 맞는지 확인하고 들고옴
    @Query("SELECT i.id, i.name, i.organization, i.email, i.phone, i.title, i.content, i.status, i.inquiryDate " +
    	       "FROM Inquiry i WHERE i.id = :id AND i.member.id = :memberId")
    	InquiryDetailDto findInquiryByIdAndMemberId(@Param("id") Long id, @Param("memberId") String memberId);
}
