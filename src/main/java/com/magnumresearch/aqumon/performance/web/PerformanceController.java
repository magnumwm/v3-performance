package com.magnumresearch.aqumon.performance.web;

import com.magnumresearch.aqumon.common.pojo.Result;
import com.magnumresearch.aqumon.common.utils.ResultUtil;
import com.magnumresearch.aqumon.performance.model.PortfolioPerformanceDay;
import com.magnumresearch.aqumon.performance.service.PerformanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@EnableAutoConfiguration
@RequestMapping("/performance")
public class PerformanceController {

    private PerformanceService performanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping("/portfolio/{portfolioId}/latest")
    @CrossOrigin
    @ApiOperation("获取portfolio最新的performance")
    public Result getLatestPerformance(@PathVariable Long portfolioId) {
        PortfolioPerformanceDay performanceDay = performanceService.findLatestByPortfolioId(portfolioId);
        return ResultUtil.success(performanceDay);
    }

}
