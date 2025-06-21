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
public class InquiryStatusHistoryDto {
	private InquiryStatus status;
	private String comment;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Builder.Default
	private Date modifiedAt = new Date();
}
