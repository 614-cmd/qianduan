package com.jeeplus.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info ()
                        .title("Jeeplus 单体版接口文档")
                        .description("文档描述")
                        .contact(new Contact ()
                                .name("jeeplus")
                                .email("117575171@qq.com")
                                .url("http://www.jeeplus.org"))
                        .version("v9.0"));
    }

    @Bean
    public GroupedOpenApi defaultGroupedOpenApi() {
        return GroupedOpenApi.builder ()
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( Operation.class))
                .group ( "全部接口" ).pathsToMatch ( "/**" )
                .packagesToScan ( "com.jeeplus" ).build ();
    }


    @Bean
    public GroupedOpenApi sysGroupedOpenApi() {
        return GroupedOpenApi.builder ()
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( Operation.class))
                .group ( "系统管理" ).pathsToMatch ( "/sys/**" )
                .packagesToScan ( "com.jeeplus" ).build ();
    }

    @Bean
    public GroupedOpenApi testGroupedOpenApi() {
        return GroupedOpenApi.builder ()
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( Operation.class))
                .group ( "测试用例" ).pathsToMatch ( "/test/**" )
                .packagesToScan ( "com.jeeplus" ).build ();
    }

    @Bean
    public GroupedOpenApi flowableGroupedOpenApi() {
        return GroupedOpenApi.builder ()
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( Operation.class))
                .group ( "流程管理" ).pathsToMatch ( "/flowable/**" )
                .packagesToScan ( "com.jeeplus" ).build ();
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder ()
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( Operation.class))
                .group ( "文件管理" ).pathsToMatch ( "/file/**" )
                .packagesToScan ( "com.jeeplus" ).build ();
    }

}
