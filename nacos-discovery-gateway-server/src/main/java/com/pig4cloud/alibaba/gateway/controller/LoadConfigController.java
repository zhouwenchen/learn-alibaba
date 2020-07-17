package com.pig4cloud.alibaba.gateway.controller;

import com.pig4cloud.alibaba.gateway.aware.GateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 */
@RestController
public class LoadConfigController {

    @Autowired
    private GateWayService gateWayService;

    @GetMapping("/initAllRoute")
    public void initAllRoute(){
        gateWayService.initAllRoute();
    }
}
