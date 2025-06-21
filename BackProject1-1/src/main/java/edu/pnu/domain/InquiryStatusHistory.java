package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class InquiryStatusHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AI
	private long id;
	private long inquiryId;
	@Enumerated(EnumType.STRING)
	private InquiryStatus status;
	private String comment;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Builder.Default
	private Date modifiedAt = new Date();
}
