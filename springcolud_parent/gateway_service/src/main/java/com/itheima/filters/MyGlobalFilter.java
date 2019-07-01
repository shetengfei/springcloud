package com.itheima.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component//注入Spring容器
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 目标：拦截所有请求，如果请求参数中，
         *  包含token。放行，
         *  如果没有，给用户提示未授权401，未授权
         */
        //获取请求参数
        System.out.println("全局filter");
        ServerHttpRequest request = exchange.getRequest();
        //获取参数
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        //获取token值
        String token = queryParams.getFirst("token");
        if (token == null) {
            //拦截，提示未授权错误，401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //拦截结束请求
            return exchange.getResponse().setComplete();
        }
        //放行
        return chain.filter(exchange);//代表放行请求
    }

    /**
     * 当前过滤器的执行顺序：
     * 数字越小，越早执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
