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
	
//	@GetMapping("/api/public/funnel")
//	public ResponseEntity<?> getCntAll(){
//		return null;
//	}
	
	@GetMapping("/api/public/funnel/{payType}")
	public ResponseEntity<?> getCntByPaytype(@PathVariable String payType) {
		FunnelDataResponse resp = funnelServ.findFunnelDataResponseByPaytype(payType);
		if(resp == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(resp);
	}
}
