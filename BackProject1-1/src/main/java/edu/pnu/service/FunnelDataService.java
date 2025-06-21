package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.FunnelData;
import edu.pnu.domain.FunnelDataResponse;
import edu.pnu.domain.OrderStageCnt;
import edu.pnu.persistence.StageCntRepository;

@Service
public class FunnelDataService {

	@Autowired StageCntRepository cntRepo;
	
	// 페이타입 별
	public FunnelDataResponse findFunnelDataResponseByPaytype(String payType) {
		FunnelDataResponse dto = new FunnelDataResponse();
		
		/// stage 구성
		List<String> stage = List.of("구매", "승인", "배송시작", "배송완료");

		/// funnelData 구성
		FunnelData data = new FunnelData();
		// payType
		data.setPayType(payType);	// 결제수단 매개변수로 받기
		
		// count
		OrderStageCnt rowCnt = cntRepo.findById(payType).orElse(null);	// 한 payType만 가져오기
		if(rowCnt == null) return null;
		
		List<Integer> count = new ArrayList<>();
		count.add(rowCnt.getPurchaseCnt());
		count.add(rowCnt.getApprovedCnt());
		count.add(rowCnt.getShippedCnt());
		count.add(rowCnt.getDeliveredCnt());
		data.setCount(count);

		// 전환율: 현단계/전단계
		// 이탈률: 1 - 전환율
		List<Double> conv = new ArrayList<>(); 
		List<Double> churn = new ArrayList<>();
		for(int i = 0 ; i < count.size() ; i++) {
			if(i == 0) {
				conv.add(100.0);
				churn.add(0.0);
			} else {
				double prev = count.get(i - 1);
				double now = count.get(i);
				
				double rate = now/prev;
				double roundConv = (Math.round(rate * 1000) * 100 )/ 1000.0;
				conv.add(roundConv);
				
				double roundChurn = (Math.round((1-rate) * 1000) * 100 )/ 1000.0;
				churn.add(roundChurn);
			}
		}
		data.setConvRate(conv);
		data.setChurnRate(churn);

		dto.setData(List.of(data));
		dto.setFunnelStages(stage);
		return dto;
	}

}
