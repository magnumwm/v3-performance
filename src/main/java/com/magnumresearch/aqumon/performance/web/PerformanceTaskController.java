package com.magnumresearch.aqumon.performance.web;

import com.magnumresearch.aqumon.common.pojo.Result;
import com.magnumresearch.aqumon.common.utils.ResultUtil;
import com.magnumresearch.aqumon.performance.service.PerformanceTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/performance/task")
public class PerformanceTaskController {

    private PerformanceTaskService performanceTaskService;

    @Autowired
    public PerformanceTaskController(PerformanceTaskService performanceTaskService) {
        this.performanceTaskService = performanceTaskService;
    }

    @PutMapping("/sync")
    @CrossOrigin
    @ApiOperation("生成当天performance")
    public Result syncPerformance(@RequestParam Long oid, @RequestParam Long pid) {
        performanceTaskService.syncPerformance(oid, pid);
        return ResultUtil.success();
    }
}
