package cn.xjjdog.bcmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * Swagger配置
 *
 * @author xjjdog
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .build();

        globalRequestParameters(docket);
        return docket;
    }

    /**
     * 基础配置
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("bcmall api")
                .description("nothing here")
                .version("1.0")
                .build();
    }

    /**
     * 配置方式1，可以在每个请求上，加一个Authorization的输入框
     */
    private void globalRequestParameters(Docket docket) {
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name("Authorization")
                .required(false)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
        docket.globalRequestParameters(Collections.singletonList(parameterBuilder.build()));

    }

    /**
     * 配置方式2,Swagger的Auth按钮
     */
    private void security(Docket docket) {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        docket.securitySchemes(Collections.singletonList(apiKey));
    }


}

