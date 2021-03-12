package stream.wortex.cloud.warehouseservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stream.wortex.cloud.warehouseservice.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ProductController {

    private final List<Product> data = new ArrayList<>();

    @PostConstruct
    private void initData() {
        data.add(new Product(1L,"Book", 123));
        data.add(new Product(2L,"Bed", 1234));
        data.add(new Product(3L,"Lamp", 1));
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return  data;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return data.stream().filter(d->d.getId().equals(id)).findFirst().orElseThrow();
    }

    @GetMapping("/product")
    public Product getProduct() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return  data.get(0);
    }

}
