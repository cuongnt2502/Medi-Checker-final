package MediChecker.MediChecker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${server.port}")
    private String swaggerServerUrl;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hệ Thống Quản Lý Bệnh Nhân API")
                        .version("1.0.0")
                        .description("API cho hệ thống quản lý bệnh nhân, đơn thuốc và phân tích tương tác thuốc")
                        .contact(new Contact()
                                .name("Hospital Management Team")
                                .email("support@hospital.com")))
                .servers(List.of(
                        new Server().url("http://10.136.83.51:"+swaggerServerUrl+"/").description("Development Server")
                ));
    }
}
