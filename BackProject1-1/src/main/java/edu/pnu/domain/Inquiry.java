package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Inquiry {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AI
	private Long id;
//	@Column(name="member_id", length = 50, nullable = false)
//	private String memberId;
	private String name;
	private String organization;
	private String phone;
	private String email;
	private String title;
	private String content;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Builder.Default
	private Date inquiryDate = new Date();
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private InquiryStatus status = InquiryStatus.UNCHECKED; 
	
	@ManyToOne(fetch = FetchType.LAZY)	// 불필요한 로딩방지
	@JoinColumn(name="member_id", referencedColumnName ="id", nullable=false)
	private Member member;
}
