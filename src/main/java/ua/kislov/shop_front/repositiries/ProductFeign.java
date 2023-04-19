package ua.kislov.shop_front.repositiries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.models.Product;

import java.util.List;


@FeignClient(
        name = "shop-back-catalog",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface ProductFeign {
    @GetMapping("/catalog")
   ResponseEntity<List<Product>> catalog(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                 @RequestParam(value = "size", required = false,
                                                         defaultValue = "10") int size,
                                                 @RequestParam(value = "sortBy", required = false,
                                                         defaultValue = "name") String sortBy);


    @GetMapping("/{id}")
   ResponseEntity<Product> product(@PathVariable("id") long id);
}

