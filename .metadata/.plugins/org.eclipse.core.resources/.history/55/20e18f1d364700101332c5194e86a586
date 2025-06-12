package edu.pnu.domain;

import java.util.List;

import jakarta.persistence.Entity;
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
public class FunnelData {
	private String payType;
	private List<Integer> count;
	private List<Double> convRate; // 이전 단계 대비 전환율
	private List<Double> churnRate; // 이전 단계 대비 이탈률
}
