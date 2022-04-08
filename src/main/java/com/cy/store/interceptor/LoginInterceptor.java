package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义一个拦截器 用于用户登录 */
public class LoginInterceptor implements HandlerInterceptor {
    // 在调用所有处理请求的方法之前被自动调用并执行

    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，没有则重定向到登录界面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url和controller的映射）
     * @return 如果返回值为true 则放行当前的请求；如果为false，则拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null) { //说明用户没有登录过系统，则重定向到Login.html页面
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }

    // 在ModelAndView对象返回之后被自动调用并执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 在整个请求所有关联的资源被执行完毕最后所执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
