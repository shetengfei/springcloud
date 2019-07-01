package com.itheima.feign;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/30
 * @description ：
 * @version: 1.0
 */
import com.itheima.config.feignConfig;
import com.itheima.domain.User;
import com.itheima.feign.impl.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign客户端接口类
 */


/**
 * FeignClient注解当前的接口是Feign客户端
 * 第一个参数：value,代表服务提供者的应用名称
 * mybatis mapper类似，使用动态代理，生成实现类。
 * 第二个参数：fallback，指定服务降级处理实现类，服务降级方法
 * 第三个参数：configuration，导入feignConfig配置
 */
@FeignClient(value = "producer-server",fallback = UserClientFallback.class,configuration = feignConfig.class )  //生产者的应用名称
public interface UserClient {

    /**
     * RequestMapping
     * Spring MVC 里注解
     * RequestMapping，反向映射，生成请求地址。Http请求。
     * @RequestParam("id") 是必须要进行请求地址参数的绑定
     * @param id
     * @return
     */
    //将要调取的生产的方法
    @RequestMapping("/user/findById")
    public User findById(@RequestParam("id") Integer id);



}
