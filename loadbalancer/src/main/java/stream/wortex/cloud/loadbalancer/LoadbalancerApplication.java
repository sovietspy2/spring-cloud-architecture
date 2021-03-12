package stream.wortex.cloud.loadbalancer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class LoadbalancerApplication {

    @Value("${warehouse}")
    private String warehouseServiceAddress;

    @Value("${payment}")
    private String paymentServiceAddress;

    public static void main(String[] args) {
        SpringApplication.run(LoadbalancerApplication.class, args);
    }


    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
