package stream.wortex.cloud.loadbalancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stream.wortex.cloud.loadbalancer.model.Product;
import stream.wortex.cloud.loadbalancer.service.WarehouseService;

import java.util.logging.Logger;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    protected Logger logger = Logger.getLogger(WarehouseController.class.getName());

    @RequestMapping("/product")
    public Product getProduct() {

        logger.info(" /product in controller");

        Product product = warehouseService.getProduct();

        logger.info("fetched: "+product.toString());
        return product;
    }

    @RequestMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) {

        logger.info(" /product in controller");

        Product product = warehouseService.getProduct(id);

        logger.info("fetched: "+product.toString());
        return product;
    }


}
