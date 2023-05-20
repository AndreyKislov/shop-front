package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.CartItemsDTO;
import ua.kislov.shop_front.dto.ProductListDTO;
import ua.kislov.shop_front.dto.UpdateQuantityDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.repositiries.ShopFeign;

@Service
public class ProductService implements ua.kislov.shop_front.services.interfaces.ProductInterface {
    private final ShopFeign shopFeign;

    @Autowired
    public ProductService(ShopFeign shopFeign) {
        this.shopFeign = shopFeign;
    }

    @Override
    public Product getProduct(long id){
        return shopFeign.product(id).getBody();
    }
    @Override
    public ProductListDTO productFindAll(int page, int size, String sortBy) {
        return shopFeign.products(page, size, sortBy).getBody();
    }
    @Override
    public void addToCart(long clientId, long productId){
        shopFeign.addToCart(new CartItemsDTO(productId, clientId));

    }
    @Override
    public void updateQuantity(UpdateQuantityDTO dto){
        shopFeign.updateQuantity(dto);
    }

    @Override
    public void deleteFromCart(CartItemsDTO dto){
         shopFeign.deleteFromCart(dto);
    }
}
