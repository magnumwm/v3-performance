package com.magnumresearch.aqumon.performance.service;

import com.magnumresearch.aqumon.common.feign.AccountsFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceTaskService {

    private PerformanceService performanceService;
    private AccountsFeignClient accountsFeignClient;

    @Autowired
    public PerformanceTaskService(PerformanceService performanceService,
                                  AccountsFeignClient accountsFeignClient) {
        this.performanceService = performanceService;
        this.accountsFeignClient = accountsFeignClient;
    }

    /**
     * 每晚生成performance
     * cronjob: 0 0 20 * * ?
     */
    public void syncPerformance(Long oid, Long pid) {
        // 更新iaccount, portfolio, 生成iaccount history以及holding history
        accountsFeignClient.backupIaccountAndPortfolio(oid, pid);

        List<Long> iaccountIds =  accountsFeignClient.getBatchIaccountIds(oid, pid).getData();
        iaccountIds.forEach(performanceService::generatePerformanceDay);
    }
}
