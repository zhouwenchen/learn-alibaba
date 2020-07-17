/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.alibaba.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 测试案例
 * http://localhost:8054/provider/demo?name=abc&token=123
 * 先指向sql脚本
 * 启动 nacos-discovery-gateway-server 和 nacos-discovery-provider 项目
 * 已经注释了 application.yml 中的路由规则
 * 项目启动先加载数据库中的路由策略
 *
 * @author zhouwenchen
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(value = "com.pig4cloud.alibaba.gateway.mapper")
public class NacosDiscoveryGatewagApplication {

    public static void main(String[] args) {


        SpringApplication.run(NacosDiscoveryGatewagApplication.class, args);
    }

}
