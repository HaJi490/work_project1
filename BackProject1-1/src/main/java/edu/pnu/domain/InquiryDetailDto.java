package edu.pnu.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InquiryDetailDto {
	private Long inquiryId;
	private String name;
	private String organization;
	private String email;
	private String phone;
	private String title;
	private String content;
	private InquiryStatus status;
	@Builder.Default
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date inquiryDate = new Date();
	private List<InquiryStatusHistoryDto> history;
	
	public InquiryDetailDto( Long id, String name, String organization, String email, String phone, 
			String title,String content, InquiryStatus status, Date inquiryDate) {
		this.inquiryId = id;
		this.name = name;
		this.organization = organization;
		this.email = email;
		this.phone = phone;
		this.title = title;
		this.content = content;
		this.inquiryDate = inquiryDate;
		this.status = status;
	}
}
