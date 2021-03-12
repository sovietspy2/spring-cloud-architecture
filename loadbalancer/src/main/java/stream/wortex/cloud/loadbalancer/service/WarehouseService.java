package stream.wortex.cloud.loadbalancer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import stream.wortex.cloud.loadbalancer.execption.BusinessErrorException;
import stream.wortex.cloud.loadbalancer.model.Product;

import java.util.logging.Logger;

@Service
@RefreshScope
public class WarehouseService {

    @Value("${warehouse}")
    private String warehouseServiceAddress;

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected Logger logger = Logger.getLogger(WarehouseService.class.getName());

    public Product getProduct() {

        try {
            logger.info(" /products hit in the loadbalancer");

            Product product = restTemplate.getForObject("http://"+warehouseServiceAddress + "/product", Product.class);
            return product;

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessErrorException();
        }
    }

    public Product getProduct(Long id) {

        try {
            logger.info(" /product/{id} hit in the loadbalancer");

            Product product = restTemplate.getForObject("http://"+warehouseServiceAddress + "/product/{id}", Product.class, id);
            return product;

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessErrorException();
        }

    }

}
