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

package com.pig4cloud.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author lengleng
 * @date 2019-12-04
 */
@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init() {
        WebCallbackManager.setUrlBlockHandler(new DemoUrlBlockHandler());
        WebCallbackManager.setRequestOriginParser(new IpRequestOriginParser());
    }
}
