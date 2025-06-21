package edu.pnu.domain;

import java.util.Date;

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
public class InquiryListDto {
	private Long inquiryId;
	private String title;
	private String organization;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date inquiryDate;
	private InquiryStatus status;
	

}
