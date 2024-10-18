package com.gamemoonchul.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    // yaml에서 관리해도 될만두
    private static final List<String> serverUrls = List.of("http://localhost:8080", "https://api.gamemuncheol.com");


    @Bean
    public OpenAPI openAPI() {
        List<Server> servers = serverUrls.stream().map(url -> {
            Server server = new Server();
            server.setUrl(url);
            return server;
        }).toList();

        return new OpenAPI()
            .servers(servers)
            .info(new Info()
                .title("Gamemuncheol Project API")
                .description("게임문철 프로젝트의 API 문서입니다.")
                .version("1.0.0"));
    }
}
