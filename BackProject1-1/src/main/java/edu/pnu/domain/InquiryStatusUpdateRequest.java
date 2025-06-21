package edu.pnu.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class InquiryStatusUpdateRequest {
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private InquiryStatus status;
	private String comment;
}
