package com.eum.pj_eum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("이음(Eum) 프로젝트 API")
                        .description("청소년 정책 AI 상담 서비스 API 문서")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("이음 개발팀")
                                .email("dev@eum.com")));
    }
}