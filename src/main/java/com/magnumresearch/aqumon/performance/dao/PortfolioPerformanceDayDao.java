package com.magnumresearch.aqumon.performance.dao;

import com.magnumresearch.aqumon.performance.model.PortfolioPerformanceDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioPerformanceDayDao extends JpaRepository<PortfolioPerformanceDay, Long> {

    PortfolioPerformanceDay findFirstByPortfolioIdOrderByReportTimeDesc(Long portfolioId);
}
