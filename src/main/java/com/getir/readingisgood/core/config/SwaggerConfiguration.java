package com.getir.readingisgood.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.getir.readingisgood")).paths(PathSelectors.any())
                .build().globalOperationParameters(List.of(new ParameterBuilder().name("Authorization").defaultValue("Bearer ").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build()))
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("Reading is Good")
                .contact(new Contact("Eray Kaya", "", "eraykaya85@gmail.com"))
                .build();
    }

}
