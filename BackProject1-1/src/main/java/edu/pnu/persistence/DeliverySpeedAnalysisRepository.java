package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.DeliverySpeedAnalysis;

@Repository
public interface DeliverySpeedAnalysisRepository extends JpaRepository<DeliverySpeedAnalysis, Long> {
    
    // Find by dataset type
    List<DeliverySpeedAnalysis> findByDataset(String dataset);
    
    // Find by dataset and category
    List<DeliverySpeedAnalysis> findByDatasetAndCategory(String dataset, String category);
    
    // Get all datasets
    @Query("SELECT d FROM DeliverySpeedAnalysis d")
    List<DeliverySpeedAnalysis> findAllDatasets();
    
    // Get delivery speed analysis data
    @Query("SELECT d FROM DeliverySpeedAnalysis d WHERE d.dataset = 'delivery_speed_analysis' ORDER BY d.value1 DESC")
    List<DeliverySpeedAnalysis> findDeliverySpeedData();
    
    // Get delivery impact data
    @Query("SELECT d FROM DeliverySpeedAnalysis d WHERE d.dataset = 'delivery_impact' ORDER BY d.category")
    List<DeliverySpeedAnalysis> findDeliveryImpactData();
    
    // Get review scores data
    @Query("SELECT d FROM DeliverySpeedAnalysis d WHERE d.dataset = 'review_scores' ORDER BY d.category")
    List<DeliverySpeedAnalysis> findReviewScoresData();
    
    // Get business metrics
    @Query("SELECT d FROM DeliverySpeedAnalysis d WHERE d.dataset = 'business_metrics'")
    List<DeliverySpeedAnalysis> findBusinessMetrics();
    
    // Get improvement scenarios
    @Query("SELECT d FROM DeliverySpeedAnalysis d WHERE d.dataset = 'improvement_scenarios' ORDER BY d.value1")
    List<DeliverySpeedAnalysis> findImprovementScenarios();
}