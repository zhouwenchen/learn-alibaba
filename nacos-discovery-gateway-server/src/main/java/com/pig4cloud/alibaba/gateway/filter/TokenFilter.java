package com.pig4cloud.alibaba.gateway.filter;

import org.apache.commons.lang3.StringUtils;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



/**
 * 自定义 token的filter 的过滤器
 */
@Component
public class TokenFilter implements GlobalFilter {

    private final String TOKEN = "token";
    private final String ERROR_MSG = "token is not null";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst(TOKEN);
        if (StringUtils.isEmpty(token)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            DataBuffer buffer = response.bufferFactory().wrap(ERROR_MSG.getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        //  使用其他的过滤器
        return chain.filter(exchange);
    }
}
