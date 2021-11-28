package com.vlpz.repository;

import com.vlpz.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    List<Statistics> findAllByUserId(Long id);
    List<Statistics> findAllByTaskId(Long id);
    Statistics getByTaskIdAndUserId(Long taskId, Long userId);
}
