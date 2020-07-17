package com.pig4cloud.alibaba.gateway.bean;

import lombok.Data;

@Data
public class GateWayEntity {
    private int id;
    private String routeId;
    private String routeName;
    private String routePattern;
    private String routeType;
    private String routeUrl;
}
