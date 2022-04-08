package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/** 处理器拦截器的配置与注册
 * 借助WebMvcConfigure接口，可以将用户定义的拦截器进行注册，才可以保证拦截器能够生效和使用。定义一个类实现该接口，并添加白名单、黑名单*/
@Configuration //自动加载当前的拦截器配置并注册
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    //将自定义的拦截器进行配置并注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建自定义的拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        // 配置白名单：存放一个List集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");
        patterns.add("/districts/**");

        //完成拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")  //表示要拦截的url是什么(黑名单)
                .excludePathPatterns(patterns);  //除以下路径以外（白名单）

    }
}
