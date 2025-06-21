package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_speed_analysis")
public class DeliverySpeedAnalysis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataset;

    @Column(nullable = false)
    private String category;

    private Double value1;

 
    private String value2;
}