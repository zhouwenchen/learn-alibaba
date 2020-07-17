package com.pig4cloud.alibaba.gateway.config;

import com.pig4cloud.alibaba.gateway.aware.GateWayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目已启动就加载，用于从数据库中获取到持久化的路由策略
 */
@Component
@Slf4j
public class RouteConfig implements CommandLineRunner {
    @Autowired
    private GateWayService gateWayService;

    @Override
    public void run(String... args) throws Exception {
      log.info("RouteConfig is start...");
      gateWayService.initAllRoute();
      log.info("RouteConfig is end...");
    }
}
