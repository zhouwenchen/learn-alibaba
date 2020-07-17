package com.pig4cloud.alibaba.gateway.aware;

import com.pig4cloud.alibaba.gateway.bean.GateWayEntity;
import com.pig4cloud.alibaba.gateway.mapper.MayiktGatewayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目启动需要从数据库中加载
 */
@Service
public class GateWayService implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Autowired
    private MayiktGatewayMapper mayiktGateway;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
      this.publisher = applicationEventPublisher;
    }

    public void initAllRoute() {
        // 从数据库查询配置的网关配置
        List<GateWayEntity> gateWayEntities = mayiktGateway.gateWayAll();
        for (GateWayEntity gw :
                gateWayEntities) {
            loadRoute(gw);
        }

    }

    public String loadRoute(GateWayEntity gateWayEntity) {
        RouteDefinition definition = new RouteDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        PredicateDefinition predicate = new PredicateDefinition();
        FilterDefinition filterDefinition = new FilterDefinition();
        Map<String, String> filterParams = new HashMap<>(8);
        // 如果配置路由type为0的话 则从注册中心获取服务
        URI uri = null;
        if (gateWayEntity.getRouteType().equals("0")) {
            uri = uri = UriComponentsBuilder.fromUriString("lb://" + gateWayEntity.getRouteUrl() + "/").build().toUri();
        } else {
            uri = UriComponentsBuilder.fromHttpUrl(gateWayEntity.getRouteUrl()).build().toUri();
        }
        // 定义的路由唯一的id
        definition.setId(gateWayEntity.getRouteId());
        predicate.setName("Path");
        //路由转发地址
        predicateParams.put("pattern", gateWayEntity.getRoutePattern());
        predicate.setArgs(predicateParams);

        // 名称是固定的, 路径去前缀
        filterDefinition.setName("StripPrefix");
        filterParams.put("_genkey_0", "1");
        filterDefinition.setArgs(filterParams);
        definition.setPredicates(Arrays.asList(predicate));
        definition.setFilters(Arrays.asList(filterDefinition));
        definition.setUri(uri);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }
}
