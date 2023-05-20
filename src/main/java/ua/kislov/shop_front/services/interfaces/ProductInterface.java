package ua.kislov.shop_front.services.interfaces;

import ua.kislov.shop_front.dto.CartItemsDTO;
import ua.kislov.shop_front.dto.ProductListDTO;
import ua.kislov.shop_front.dto.UpdateQuantityDTO;
import ua.kislov.shop_front.models.Product;

public interface ProductInterface {
    Product getProduct(long id);

    ProductListDTO productFindAll(int page, int size, String sortBy);

    void addToCart(long clientId, long productId);

    void updateQuantity(UpdateQuantityDTO dto);

    void deleteFromCart(CartItemsDTO dto);
}
