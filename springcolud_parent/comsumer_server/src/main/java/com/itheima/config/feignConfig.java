package com.itheima.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/30
 * @description ：
 * @version: 1.0
 */
/*
*  @Configuration 表示当前类是一个配置类
*  /**
 * Bean 把当前对象注入到spring容器中
 * <bean id= class=></>
 * @return
 */
/**
 *  NONE No logging.不做日志
 */
/**
 * BASIC Log only the request method and URL and the response status code and execution time.
 * 仅仅是只记录请求方法，URL地址，响应状态码和执行时间
 */
/**
 * HEADERS Log the basic information along with request and response headers.
 * headers：记录基本信息，请求和响应的头
 */
/**
 * FULL Log the headers, body, and metadata for both requests and responses.
 * 全部：请求和响应的头、体、元数据
 */

@Configuration
public class feignConfig {


    @Bean
    public Logger.Level feignLoggerLevel()

    {
        return Logger.Level.FULL;
    }

}