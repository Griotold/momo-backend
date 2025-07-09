package com.momo.backend.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                .name(jwt)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//        );

        return new OpenAPI()
                .info(apiInfo());
//                .addSecurityItem(securityRequirement)
//                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Momo Backend API")
                .description("Momo 프로젝트 백엔드 API 문서 (Mock 버전)")
                .version("0.0.1");
    }
}
