package stream.wortex.cloud.warehouseservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stream.wortex.cloud.warehouseservice.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class.getName());

    private final List<Product> data = new ArrayList<>();

    @PostConstruct
    private void initData() {
        data.add(new Product(1L,"Book", 123));
        data.add(new Product(2L,"Bed", 1234));
        data.add(new Product(3L,"Lamp", 1));
    }

    @GetMapping("/products")
    public List<Product> getProducts(@AuthenticationPrincipal Jwt jwt) {
        log.info(String.format("Resource accessed by: %s (with subjectId: %s)" ,
                jwt.getClaims().get("user_name"),
                jwt.getSubject()));
        return  data;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        log.info(String.format("Resource accessed by: %s (with subjectId: %s)" ,
                jwt.getClaims().get("user_name"),
                jwt.getSubject()));
        log.info("/product/id accessed");
        System.out.println("/product/id");
        return data.stream().filter(d->d.getId().equals(id)).findFirst().orElseThrow();
    }

    @GetMapping("/product")
    public Product getProduct(@AuthenticationPrincipal Jwt jwt) throws InterruptedException {
        log.info(String.format("Resource accessed by: %s (with subjectId: %s)" ,
                jwt.getClaims().get("user_name"),
                jwt.getSubject()));
        log.info(" /product accessed");
        System.out.println("/product");
        TimeUnit.SECONDS.sleep(2);
        return  data.get(0);
    }

}
