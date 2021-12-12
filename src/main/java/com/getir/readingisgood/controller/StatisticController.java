package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.Statistic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.getir.readingisgood.service.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Statistic Controller"}, description = "Order statistics")
@RestController
@RequestMapping(value = "/api/v1",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class StatisticController
{
    private final StatisticService statisticService;

    @GetMapping("monthly-statistics")
    @ApiOperation(value = "Monthly Statistic of Orders")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Monthly Statistic of Orders"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<List<Statistic>> statistic()
    {
        return new ResponseEntity<>(statisticService.listStatistic(), HttpStatus.OK);
    }
}
