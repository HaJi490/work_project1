package edu.pnu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.DeliverySpeedAnalysis;
import edu.pnu.service.DeliverySpeedAnalysisService;

@RestController
@RequestMapping("/api/delivery-analysis")
@CrossOrigin(origins = "*")
public class DeliverySpeedAnalysisController {

	@Autowired
    private DeliverySpeedAnalysisService service;
    
    @GetMapping
    public ResponseEntity<List<DeliverySpeedAnalysis>> getAllData() {
        return ResponseEntity.ok(service.getAllData());
    }
    
    @GetMapping("/datasets")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getAllDatasets() {
        return ResponseEntity.ok(service.getAllDatasets());
    }
    
    @GetMapping("/dataset/{dataset}")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getDataByDataset(@PathVariable String dataset) {
        return ResponseEntity.ok(service.getDataByDataset(dataset));
    }
    
    @GetMapping("/dataset/{dataset}/category/{category}")
    public ResponseEntity<DeliverySpeedAnalysis> getDataByDatasetAndCategory(
            @PathVariable String dataset, 
            @PathVariable String category) {
        DeliverySpeedAnalysis data = service.getDataByDatasetAndCategory(dataset, category);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }
    
    // Specific endpoint for each dataset
    @GetMapping("/delivery-speed")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getDeliverySpeedAnalysis() {
        return ResponseEntity.ok(service.getDeliverySpeedAnalysis());
    }
    
    @GetMapping("/delivery-impact")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getDeliveryImpactData() {
        return ResponseEntity.ok(service.getDeliveryImpactData());
    }
    
    @GetMapping("/review-scores")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getReviewScoresData() {
        return ResponseEntity.ok(service.getReviewScoresData());
    }
    
    @GetMapping("/business-metrics")
    public ResponseEntity<Map<String, Double>> getBusinessMetrics() {
        return ResponseEntity.ok(service.getBusinessMetrics());
    }
    
    @GetMapping("/improvement-scenarios")
    public ResponseEntity<List<DeliverySpeedAnalysis>> getImprovementScenarios() {
        return ResponseEntity.ok(service.getImprovementScenarios());
    }
    
    // Analytics endpoints
    @GetMapping("/analytics/average-delivery-time")
    public ResponseEntity<Double> getAverageDeliveryTime() {
        return ResponseEntity.ok(service.getAverageDeliveryTime());
    }
    
    @GetMapping("/analytics/repurchase-rate-1m")
    public ResponseEntity<Double> getRepurchaseRate1M() {
        return ResponseEntity.ok(service.getRepurchaseRate1M());
    }
    
    @GetMapping("/analytics/repurchase-rate-6m")
    public ResponseEntity<Double> getRepurchaseRate6M() {
        return ResponseEntity.ok(service.getRepurchaseRate6M());
    }
    
    @GetMapping("/analytics/total-customers")
    public ResponseEntity<Integer> getTotalCustomers() {
        return ResponseEntity.ok(service.getTotalCustomers());
    }
    
    @GetMapping("/analytics/average-order-value")
    public ResponseEntity<Double> getAverageOrderValue() {
        return ResponseEntity.ok(service.getAverageOrderValue());
    }
    
    @PostMapping
    public ResponseEntity<DeliverySpeedAnalysis> createData(@RequestBody DeliverySpeedAnalysis data) {
        return ResponseEntity.ok(service.saveData(data));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}