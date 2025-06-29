package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.OrderStageCnt;

@Repository
public interface StageCntRepository extends JpaRepository<OrderStageCnt, String> {
}
