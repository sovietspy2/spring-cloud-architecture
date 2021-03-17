package stream.wortex.cloud.loadbalancer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import stream.wortex.cloud.loadbalancer.execption.BusinessErrorException;
import stream.wortex.cloud.loadbalancer.model.Product;

import java.util.logging.Logger;

@Service
@RefreshScope
public class WarehouseService {

    @Value("${warehouseAddress}")
    private String warehouseServiceAddress;

    @Autowired
    private RestTemplateComponent restTemplateComponet;

    protected Logger logger = Logger.getLogger(WarehouseService.class.getName());

    public Product getProduct() {

        try {
            logger.info(" /products hit in the loadbalancer");

            Product product = restTemplateComponet.getRestTemplate().getForObject("http://"+warehouseServiceAddress + "/product", Product.class);
            return product;

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessErrorException();
        }
    }

    public Product getProduct(Long id) {

        try {
            logger.info(" /product/{id} hit in the loadbalancer");

            Product product = restTemplateComponet.getRestTemplate().getForObject("http://"+warehouseServiceAddress + "/product/{id}", Product.class, id);
            return product;

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessErrorException();
        }

    }

}
