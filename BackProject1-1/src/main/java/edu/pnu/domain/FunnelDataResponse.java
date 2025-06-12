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
public class FunnelDataResponse {
	@JsonProperty("funnel_stages")	// json응답이름 설정
	private List<String> funnelStages;
	private List<FunnelData> data;	// 원하는 json 리턴형식하려면 리스트로 리턴---------------------------------------!
}
