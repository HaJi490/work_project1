package edu.pnu.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pnu.domain.DeliverySpeedAnalysis;
import edu.pnu.persistence.DeliverySpeedAnalysisRepository;

@Service
//@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliverySpeedAnalysisService {

    private DeliverySpeedAnalysisRepository repository;
    
    public DeliverySpeedAnalysisService(DeliverySpeedAnalysisRepository repository) {
    	this.repository = repository;
	}
    
    public List<DeliverySpeedAnalysis> getAllData() {
        return repository.findAll();
    }
    
    public List<DeliverySpeedAnalysis> getDataByDataset(String dataset) {
        return repository.findByDataset(dataset);
    }
    
    public List<DeliverySpeedAnalysis> getAllDatasets() {
        return repository.findAllDatasets();
    }
    
    public DeliverySpeedAnalysis getDataByDatasetAndCategory(String dataset, String category) {
        List<DeliverySpeedAnalysis> result = repository.findByDatasetAndCategory(dataset, category);
        return result.isEmpty() ? null : result.get(0);
    }
    
    // Specific business methods
    public List<DeliverySpeedAnalysis> getDeliverySpeedAnalysis() {
        return repository.findDeliverySpeedData();
    }
    
    public List<DeliverySpeedAnalysis> getDeliveryImpactData() {
        return repository.findDeliveryImpactData();
    }
    
    public List<DeliverySpeedAnalysis> getReviewScoresData() {
        return repository.findReviewScoresData();
    }
    
    public Map<String, Double> getBusinessMetrics() {
//        return repository.findBusinessMetrics().stream()
//                .collect(Collectors.toMap(
//                    DeliverySpeedAnalysis::getCategory,
//                    DeliverySpeedAnalysis::getValue1
//                ));
    	List<DeliverySpeedAnalysis> list = repository.findBusinessMetrics();
    	Map<String, Double> map = new HashMap<>();
    	for(DeliverySpeedAnalysis d : list) {
    		map.put(d.getCategory(), d.getValue1());
    	}
    	return map;
    }
    
    public List<DeliverySpeedAnalysis> getImprovementScenarios() {
        return repository.findImprovementScenarios();
    }
    
    // Analytics methods
    public double getAverageDeliveryTime() {
        return getBusinessMetrics().getOrDefault("avgDeliveryTime", 0.0);
    }
    
    public double getRepurchaseRate1M() {
        return getBusinessMetrics().getOrDefault("repurchaseRate1M", 0.0);
    }
    
    public double getRepurchaseRate6M() {
        return getBusinessMetrics().getOrDefault("repurchaseRate6M", 0.0);
    }
    
    public int getTotalCustomers() {
        return getBusinessMetrics().getOrDefault("totalCustomers", 0.0).intValue();
    }
    
    public double getAverageOrderValue() {
        return getBusinessMetrics().getOrDefault("avgOrderValue", 0.0);
    }
    
    @Transactional
    public DeliverySpeedAnalysis saveData(DeliverySpeedAnalysis data) {
        return repository.save(data);
    }
    
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}