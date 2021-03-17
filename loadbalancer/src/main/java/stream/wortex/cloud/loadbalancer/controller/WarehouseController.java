package stream.wortex.cloud.loadbalancer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stream.wortex.cloud.loadbalancer.model.Product;
import stream.wortex.cloud.loadbalancer.service.WarehouseService;


@RestController
public class WarehouseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @HystrixCommand(fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="80"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),

            })
    @RequestMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) {

        logger.info(" /product/{} in controller");

        Product product = warehouseService.getProduct(id);

        logger.info("fetched: "+product.toString());
        return product;
    }

    @HystrixCommand(fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="80"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            })
    @RequestMapping("/product")
    public Product getProduct() {

        logger.info(" /product in controller");

        Product product = warehouseService.getProduct();

        logger.info("fetched: "+product.toString());
        return product;
    }


    public Product fallback(Throwable throwable) {
        //log.error("fallback!!! "+throwable.getMessage());
        return new Product();
    }

    public Product fallback(Long id,Throwable throwable) {
        //log.error("fallback "+id+throwable.getMessage());


        /// send back something from cache

        Product cachedVersion = new Product(1L,"cached version",123);
        return cachedVersion;
    }


}
