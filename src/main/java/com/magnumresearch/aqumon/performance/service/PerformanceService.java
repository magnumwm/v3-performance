package com.magnumresearch.aqumon.performance.service;

import com.magnumresearch.aqumon.accounts.account.model.IaccountHistory;
import com.magnumresearch.aqumon.accounts.portfolio.model.Portfolio;
import com.magnumresearch.aqumon.common.feign.AccountsFeignClient;
import com.magnumresearch.aqumon.common.feign.PortfolioFeignClient;
import com.magnumresearch.aqumon.common.utils.DateUtil;
import com.magnumresearch.aqumon.performance.dao.PortfolioPerformanceDayDao;
import com.magnumresearch.aqumon.performance.model.PortfolioPerformanceDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PerformanceService {

    private PortfolioPerformanceDayDao performanceDayDao;
    private AccountsFeignClient accountsFeignClient;
    private PortfolioFeignClient portfolioFeignClient;

    @Autowired
    public PerformanceService(PortfolioPerformanceDayDao performanceDayDao,
                              AccountsFeignClient accountsFeignClient,
                              PortfolioFeignClient portfolioFeignClient) {
        this.performanceDayDao = performanceDayDao;
        this.accountsFeignClient = accountsFeignClient;
        this.portfolioFeignClient = portfolioFeignClient;
    }

    public PortfolioPerformanceDay findLatestByPortfolioId(Long portfolioId) {
        return performanceDayDao.findFirstByPortfolioIdOrderByReportTimeDesc(portfolioId);
    }

    public PortfolioPerformanceDay generatePerformanceDay(Long iaccountId) {
        Portfolio portfolio = portfolioFeignClient.getPortfolioByIaccountId(iaccountId).getData();
        Integer reportDay = Integer.valueOf(DateUtil.format(new Date(), DateUtil.YYYYMMDD));
        IaccountHistory iaccountHistory = accountsFeignClient.getIaccountHistory(iaccountId, reportDay).getData();
        PortfolioPerformanceDay prevPerformanceDay = findLatestByPortfolioId(portfolio.getId());

        PortfolioPerformanceDay performanceDay = new PortfolioPerformanceDay();
        performanceDay.setOid(iaccountHistory.getOid());
        performanceDay.setPid(iaccountHistory.getPid());
        performanceDay.setUid(iaccountHistory.getUid());
        performanceDay.setIaccountId(iaccountId);
        performanceDay.setPortfolioId(portfolio.getId());
        performanceDay.setCurrency(iaccountHistory.getCurrency());
        performanceDay.setAccumulatedInvestedAmount(iaccountHistory.getAccumulatedInvestedAmount());
        performanceDay.setAccumulatedPl(iaccountHistory.getAccumulatedPl());
        performanceDay.setReportTime(System.currentTimeMillis());
        performanceDay.setStatus(portfolio.getStatus());
        performanceDay.setValue(iaccountHistory.getTotalValue());

        if (prevPerformanceDay == null) {
            performanceDay.setMoneyFlow(performanceDay.getAccumulatedInvestedAmount());
            performanceDay.setCurrentDayPl(performanceDay.getAccumulatedPl());
            performanceDay.setAccumulatedValueAmount(performanceDay.getAccumulatedInvestedAmount().add(performanceDay.getAccumulatedPl()));
            performanceDay.setReturnRatio(performanceDay.getAccumulatedPl().divide(performanceDay.getAccumulatedInvestedAmount(), 6, BigDecimal.ROUND_HALF_UP));
            performanceDay.setTwrRatio(performanceDay.getReturnRatio());
        } else {
            performanceDay.setMoneyFlow(performanceDay.getAccumulatedInvestedAmount().subtract(prevPerformanceDay.getAccumulatedInvestedAmount()));
            performanceDay.setCurrentDayPl(performanceDay.getAccumulatedPl().subtract(prevPerformanceDay.getAccumulatedPl()));
            performanceDay.setAccumulatedValueAmount(performanceDay.getAccumulatedInvestedAmount().add(performanceDay.getAccumulatedPl()));
            performanceDay.setReturnRatio((performanceDay.getAccumulatedValueAmount().subtract(performanceDay.getMoneyFlow())).
                    divide(prevPerformanceDay.getAccumulatedValueAmount(), 6, RoundingMode.HALF_UP).subtract(BigDecimal.ONE));
            performanceDay.setTwrRatio((performanceDay.getReturnRatio().add(BigDecimal.ONE)).
                    multiply(prevPerformanceDay.getTwrRatio().add(BigDecimal.ONE)).subtract(BigDecimal.ONE));

        }
        return performanceDayDao.save(performanceDay);
    }


}
