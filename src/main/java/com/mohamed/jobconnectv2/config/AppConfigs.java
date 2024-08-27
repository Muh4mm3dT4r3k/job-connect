package com.mohamed.jobconnectv2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigs {

    @Bean
    public OpenAPI customOpenApi() {
        String bearerAuth = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(bearerAuth))
                .components(
                        new Components()
                                .addSecuritySchemes(bearerAuth,
                                        new SecurityScheme()
                                                .name(bearerAuth)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                        )
                ).info(new Info()
                        .title("Job Connect API")
                        .version("1.0")
                        .description("API documentation for Job Connect application")
                );
    }
}
