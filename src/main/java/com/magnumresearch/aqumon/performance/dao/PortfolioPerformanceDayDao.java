package com.magnumresearch.aqumon.performance.dao;

import com.magnumresearch.aqumon.performance.model.PortfolioPerformanceDay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioPerformanceDayDao extends JpaRepository<PortfolioPerformanceDay, Long> {

    PortfolioPerformanceDay findFirstByPortfolioIdOrderByReportDate(Long portfolioId);
}
