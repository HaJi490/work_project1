package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.FunnelDataResponse;
import edu.pnu.service.FunnelDataService;

@RestController
public class FunnelDataController {
	@Autowired FunnelDataService funnelServ;

	@GetMapping("/api/public/funnel/{payType}")
	public ResponseEntity<?> getPaytypeCnt(@PathVariable String payType) {
		FunnelDataResponse resp = funnelServ.findFunnelDataResponse(payType);
		if(resp == null) return ResponseEntity.notFound().build();
		else return ResponseEntity.ok(resp);
	}
}
