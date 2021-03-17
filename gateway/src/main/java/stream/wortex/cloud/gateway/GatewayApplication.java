package stream.wortex.cloud.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("r1", r -> r.host("*")
                        .and()
                        .path("/product/**")
                        .filters(f ->
                                f.filters(filterFactory.apply())
                                .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                .addRequestHeader("X-TestRequestHeader", "test")
                                .removeRequestHeader("Cookie")
                        )
                        .uri("http://localhost:8080"))
                .route("r1", r -> r.host("*")
                        .and()
                        .path("/product")
                        .filters(f ->
                                f.filters(filterFactory.apply())
                                .addResponseHeader("X-TestHeader", "this_is_a_test_res_header")
                                .addRequestHeader("X-TestRequestHeader", "test")
                                        .removeRequestHeader("Cookie")
                        )
                        .uri("http://localhost:8080"))
                .build();
    }

//    @RequestMapping("/secure")
//    public List<String> secure( @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//                        @AuthenticationPrincipal OAuth2User oauth2User) {
//            return List.of(oauth2User.getName(),authorizedClient.getClientRegistration().getClientName());
////            model.addAttribute("userName", );
////            model.addAttribute("clientName", );
////            model.addAttribute("userAttributes", );
////            return "index";
//        }
//
//    @RequestMapping("/test")
//    public String secure() {
//        return "Hello test";
//    }

}
