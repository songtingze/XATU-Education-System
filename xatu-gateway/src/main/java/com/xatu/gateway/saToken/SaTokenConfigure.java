package com.xatu.gateway.saToken;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;


/**
 * @author Wang Lei
 */
@Configuration
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
//                // 拦截地址
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico")
                .addExclude("/sys/login")
                .addExclude("/user/tea/login")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/user/stu/login", r -> StpUtil.checkLogin());
                    // 权限认证 -- 不同模块, 校验不同权限
//                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    // 设置错误返回格式为JSON,跨域访问
                    ServerWebExchange exchange = SaReactorSyncHolder.getContext();
                    exchange.getResponse().getHeaders().set("Content-Type", "application/json; charset=utf-8");
                    exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", " http://localhost:8080");
//                    exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", " http://前端部署IP:8080");
                    exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
                    try {
                        //处理NotLoginException异常
                        return GlobalException.handlerNotLoginException((NotLoginException) e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return null;
                });

//                .setBeforeAuth(obj -> {
//                    // ---------- 设置跨域响应头 ----------
//                    SaHolder.getResponse()
//                            // 允许指定域访问跨域资源
//                            .setHeader("Access-Control-Allow-Origin", "*")
//                            // 允许所有请求方式
//                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
//                            // 有效时间
//                            .setHeader("Access-Control-Max-Age", "3600")
//                            // 允许的header参数
//                            .setHeader("Access-Control-Allow-Headers", "*");
//
////                    // 如果是预检请求，则立即返回到前端
////                    SaRouter.match(SaHttpMethod.OPTIONS)
////                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
////                            .back();
//                });
    }
}
