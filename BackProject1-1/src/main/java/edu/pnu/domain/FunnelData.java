package edu.pnu.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class FunnelData {
	@JsonProperty("payment_type")
	private String payType;
	private List<Integer> count;
	@JsonProperty("conversion_rates")
	private List<Double> convRate; // 이전 단계 대비 전환율
	@JsonProperty("churn_rates")
	private List<Double> churnRate; // 이전 단계 대비 이탈률
}
