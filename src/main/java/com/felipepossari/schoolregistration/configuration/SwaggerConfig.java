package com.felipepossari.schoolregistration.configuration;

import com.felipepossari.schoolregistration.application.exception.ErrorReason;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket greetingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.felipepossari"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("School Registration API")
                .description("<h3>Error codes</h3>" + buildErrorCodeTable())
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .build();
    }

    private String buildErrorCodeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"border: 1px solid black;\">");
        sb.append("<tr><td>Code</td><td>Message</td></tr>");
        for (ErrorReason e : ErrorReason.values()) {
            sb.append("<tr>");
            sb.append("<td>").append(e.getCode()).append("</td>");
            sb.append("<td>").append(e.getMessage()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}