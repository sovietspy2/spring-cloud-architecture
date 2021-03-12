package stream.wortex.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("r1", r -> r.host("**.wortex.stream")
                        .and()
                        .path("/product/**")
                        .filters(f ->
                                f
                                .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                .addRequestHeader("X-TestRequestHeader", "test")
                        )
                        .uri("http://localhost:8080"))
                .route("r1", r -> r.host("**.wortex.stream")
                        .and()
                        .path("/product")
                        .filters(f ->
                                f
                                .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                .addRequestHeader("X-TestRequestHeader", "test")
                        )
                        .uri("http://localhost:8080"))
                .build();
    }

}
