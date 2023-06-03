package com.xatu.gateway.config;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


@Primary
@Component
public class SwaggerConfig implements SwaggerResourcesProvider {
    // 默认访问路径
    public static final String API_URI = "/v3/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    public SwaggerConfig(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        // swagger分组list
        List<SwaggerResource> resources = new ArrayList<>();
        // yml配置文件路由服务名list
        List<String> routes = new ArrayList<>();
        // 遍历yml文件 循环添加入list
        routeLocator.getRoutes().subscribe(route -> {
            routes.add(route.getId());
        });
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> {
                    try {
                        routeDefinition.getPredicates().stream()
                                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                                .filter(predicateDefinition-> routeDefinition.getId().contains("swagger"))// 拦截路由服务名，是否包含swagger字符串
                                .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                                .replace("/**", API_URI))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(resources);
        return resources;
    }

    // swagger接口文档的数据源
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}