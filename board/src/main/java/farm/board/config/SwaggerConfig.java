package farm.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Import(BeanValidatorPluginsConfiguration.class) //Bean Validation 라이브러리를 사용하여 데이터 유요성 검사 수행하기 위한 구성 제공
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("farm.board.controller")) //package에 있는 모든 클래스를 문서화 대상으로 설정
                .paths(PathSelectors.any()) // 모든 api 엔드포인트를 문서화 대상으로 포함
                .build()
                .securitySchemes(List.of(apiKey())) //api 보안 스키마 설정
                .securityContexts(List.of(securityContext())); //
    }

    private ApiInfo apiInfo() { //API 문서의 메타데이터 설정
        return new ApiInfoBuilder()
                .title("Spring Kim Board")
                .description("Spring board REST API Documentation")
                .license("jaeyeon423@gmail.com")
                .licenseUrl("https://github.com/jaeyeon423")
                .version("1.0")
                .build();
    }

    private static ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer Token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(oc -> oc.requestMappingPattern().startsWith("/api/")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "global access");
        return List.of(new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
    }
}
