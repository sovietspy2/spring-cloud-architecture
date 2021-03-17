package stream.wortex.cloud.loadbalancer.service;

import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RestTemplateComponent {

    private RestTemplate restTemplate;

    @Bean(name = "oauth2RestTemplate")
    public RestTemplate restTemplate(SpringClientFactory clientFactory) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails());
        RibbonLoadBalancerClient ribbonLoadBalancerClient = new RibbonLoadBalancerClient(clientFactory);
        LoadBalancerInterceptor loadBalancerInterceptor = new LoadBalancerInterceptor(ribbonLoadBalancerClient);
        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        accessTokenProvider.setInterceptors(Arrays.asList(loadBalancerInterceptor));
        restTemplate.setAccessTokenProvider(accessTokenProvider);
        this.restTemplate = restTemplate;
        return restTemplate;
    }

    public ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId("1");
        details.setClientId("gateway");
        details.setClientSecret("secret");
        details.setAccessTokenUri("http://127.0.0.1:8090/uaa/token_keys");
        details.setId("gateway");
        details.setClientSecret("secret");
        details.setGrantType("authorization_code");
        details.setScope(List.of( "openid","profile","email","resource.read"));
        return details;
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }
}
