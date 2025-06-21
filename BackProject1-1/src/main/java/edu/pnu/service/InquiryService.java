package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Inquiry;
import edu.pnu.domain.InquiryDetailDto;
import edu.pnu.domain.InquiryListDto;
import edu.pnu.domain.InquiryStatus;
import edu.pnu.domain.InquiryStatusHistory;
import edu.pnu.domain.InquiryStatusHistoryDto;
import edu.pnu.domain.InquiryStatusUpdateRequest;
import edu.pnu.persistence.InquiryRepository;
import edu.pnu.persistence.InquiryStatusRepository;

@Service
public class InquiryService {
	@Autowired private InquiryRepository inquiryRepo;
	@Autowired private InquiryStatusRepository statusRepo;
	
	/// Member
	// 분석 요청
	public void saveInquiry(Inquiry inquiry) {
		if(inquiry.getInquiryDate() == null) inquiry.setInquiryDate(new Date());
		if(inquiry.getStatus() == null) inquiry.setStatus(InquiryStatus.UNCHECKED);
		inquiryRepo.save(inquiry);
	}
	
	// 내가 요청한 목록
	public List<InquiryListDto> getMyList(String memId) {
		return inquiryRepo.findAllByMemberId(memId);
	}
	
	// 내 분석 상세보기
	public InquiryDetailDto getMyInquiryDetail(long id, String memId) {
		InquiryDetailDto resp = inquiryRepo.findInquiryByIdAndMemberId(id, memId);
		List<InquiryStatusHistoryDto> history = statusRepo.findStatusHistoryById(id);
		if(!history.isEmpty()) {
			resp.setHistory(history);
		}
		return resp;
	}
	
	/// Manager
	// 전체분석 요청 목록
	public List<InquiryListDto> getAllList() {
		List<Inquiry> repolist = inquiryRepo.findAll();
		List<InquiryListDto> dtolist = new ArrayList<>();
		for(Inquiry repo : repolist) {
			InquiryListDto dto = new InquiryListDto();
			dto.setInquiryId(repo.getId());
			dto.setTitle(repo.getTitle());
			dto.setOrganization(repo.getOrganization());
			dto.setInquiryDate(repo.getInquiryDate());
			dto.setStatus(repo.getStatus());
			dtolist.add(dto);
		}
		return dtolist;
	}
	
	// 특정 분석 상세보기
	public InquiryDetailDto getInquiryDetail(long id) {
		InquiryDetailDto resp = inquiryRepo.findInquiryById(id);
		List<InquiryStatusHistoryDto> history = statusRepo.findStatusHistoryById(id);
		if(!history.isEmpty()) {
			resp.setHistory(history);
		}
		return resp;
	}
	
	// 분석 상태, 상담 내용 업데이트
	public void updateStatus(long id, InquiryStatusUpdateRequest history) {
		Inquiry stupdate = inquiryRepo.findById(id).get();
		stupdate.setStatus(history.getStatus());
		
		InquiryStatusHistory histoSave = new InquiryStatusHistory();
		histoSave.setInquiryId(id);
		histoSave.setStatus(history.getStatus());
		histoSave.setComment(history.getComment());
		
		inquiryRepo.save(stupdate);  
		statusRepo.save(histoSave); 
	}
}
