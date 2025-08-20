package com.kt_giga_fms.car.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Service API")
                        .description("차량 관리 서비스 API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("KT Giga FMS Team")
                                .email("support@kt-giga-fms.com")
                                .url("https://kt-giga-fms.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://car-services-e6bmdpbjcffqfzd2.koreacentral-01.azurewebsites.net")
                                .description("Azure Production Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")
                ));
    }
}
