package com.magnumresearch.aqumon.performance.service;

import com.magnumresearch.aqumon.performance.dao.PortfolioPerformanceDayDao;
import com.magnumresearch.aqumon.performance.model.PortfolioPerformanceDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PerformanceService {

    private PortfolioPerformanceDayDao performanceDayDao;

    @Autowired
    public PerformanceService(PortfolioPerformanceDayDao performanceDayDao) {
        this.performanceDayDao = performanceDayDao;
    }

    public PortfolioPerformanceDay findLatestByPortfolioId(Long portfolioId) {
        return performanceDayDao.findFirstByPortfolioIdOrderByReportTimeDesc(portfolioId);
    }
}
