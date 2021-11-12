package com.road.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 14:20
 * @Since： 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(securityScheme()) // 设置请求头
                .securityContexts(securityContexts()) // 设置请求路径
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.road.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo apiInfo(){
        Contact contact = new Contact("asherzzc",null , "randytaylor@163.com");

        return new ApiInfo(
                "接口文档" ,
                "接口文档",
                "1.0",
                null,
                contact,
                null ,
                null,
                new ArrayList<>());
    }

    /**
     * 创建请求头
     * @return
     */
    public List<ApiKey> securityScheme(){
        List<ApiKey> result = new ArrayList<>();
        /**
         * @Param1 : 请求头别名
         * @Param2 : 请求头实际名称
         * @Param3 : 在请求内容中充当什么角色
         */
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return result;
    }

    /**
     * 设置需要登录的路径
     * @return
     */
    public List<SecurityContext> securityContexts(){
        List<SecurityContext> result = new ArrayList<>();
        result.add(getPathByContext("/hello/.*"));
        return result;
    }

    /**
     * 获得具体路径
     * @param regex
     * @return
     */
    private SecurityContext getPathByContext(String regex) {
        return SecurityContext.builder()
                .securityReferences(defaultPath())
                .forPaths(PathSelectors.regex(regex))
                .build();
    }

    /**
     * 设置路径可涉及到的范围
     * @return
     */
    private List<SecurityReference> defaultPath() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope scope = new AuthorizationScope("global", "Access All");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;

        result.add(new SecurityReference("Authorization", scopes));
        return result;
    }
}
