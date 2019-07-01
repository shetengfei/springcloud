package com.itheima.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * 命名有要求：
 * MyParam(自定义) GatewayFilterFactory(固定写法)
 */
@Component//千万不要忘记注入Spring容器
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.Config> {
    /**
     * Prefix key.
     */
    public static final String PARAM_NAME = "name";


    public MyParamGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_NAME);
    }

    public GatewayFilter apply(Config config) {

        GatewayFilter gatewayFilter  = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                /**
                 * 拦截所有请求，如果参数中包含name,把参数打印到控制台
                 */
                //获取请求对象
                ServerHttpRequest request = exchange.getRequest();
                //获取所有参数
                String name = request.getQueryParams().getFirst("name");
                //如果name有值 = null "" "  " "   "
                if (!StringUtils.isEmpty(name)) {
                    System.out.printf("局部过滤器，拦截 name=%s !!!!", name);
                }
                return chain.filter(exchange);//放行所有请求
            }
        };
        return gatewayFilter;
    }

    public static class Config {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
