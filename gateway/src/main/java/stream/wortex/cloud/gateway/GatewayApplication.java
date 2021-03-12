package stream.wortex.cloud.gateway;

//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    public static final String TEST_CIRCUIT_BREAKER = "testCircuitBreaker";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("r1", r -> r.host("**.wortex.stream")
                        .and()
                        .path("/product/**")
                        .filters(f ->
                            f
                                    .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                    .addRequestHeader("X-TestRequestHeader","test")
//                                    .circuitBreaker(c -> c.setName(TEST_CIRCUIT_BREAKER)
//                                                          .setFallbackUri("forward:/fallback"))
                        )
                        .uri("http://localhost:8080"))
                .route("r1", r -> r.host("**.wortex.stream")
                        .and()
                        .path("/product")
                        .filters(f ->
                                f
                                        .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                        .addRequestHeader("X-TestRequestHeader","test")
//                                        .circuitBreaker(c -> c.setName(TEST_CIRCUIT_BREAKER)
//                                                .setFallbackUri("forward:/fallback"))
                        )
                        .uri("http://localhost:8080"))
        .build();
    }

//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> testCircuitBreakerCustomizer() {
//        return factory -> {
//            factory .configure(builder -> builder
//                    .timeLimiterConfig(TimeLimiterConfig.custom()
//                            .timeoutDuration(Duration.ofSeconds(1))
//                            .build())
//                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                            .failureRateThreshold(2)
//                            .waitDurationInOpenState(Duration.ofSeconds(15))
//                            .slidingWindowSize(2)
//                            .minimumNumberOfCalls(2)
//                            .build()), TEST_CIRCUIT_BREAKER);
//        };
//    }
}
