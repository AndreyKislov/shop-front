package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.repositiries.ProductFeign;

import java.util.List;

@Service
public class ProductService {
    private final ProductFeign productFeign;

    @Autowired
    public ProductService(ProductFeign productFeign) {
        this.productFeign = productFeign;
    }

    public List<Product> catalog(int page, int size, String sort){
        return productFeign.catalog(page, size, sort).getBody();
    }
    public Product product(long id){
        return productFeign.product(id).getBody();
    }
}
