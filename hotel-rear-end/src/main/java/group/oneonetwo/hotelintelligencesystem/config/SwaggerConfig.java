package group.oneonetwo.hotelintelligencesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 文
 * @description swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 自定义标识
     */
//    @Value("${jwt.header}")
    public final static String TOKEN_HEADER = "Authorization";

    /**
     * 前缀
     */
//    @Value("${jwt.token-prefix}")
    public final static String TOKEN_PREFIX = "Bearer";

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name(TOKEN_HEADER).description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(TOKEN_PREFIX + " ")
                .required(true)
                .build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("group.oneonetwo.hotelintelligencesystem"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("酒店智慧系统-接口管理")
                        .description("HotelIntelligenceSystem by one-one-two club")
                        .version("1.0")
                        .contact(new Contact("Tomato_Simon"," "," "))
                        .build())
                .globalOperationParameters(pars);
    }

}
