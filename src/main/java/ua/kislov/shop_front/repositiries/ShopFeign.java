package ua.kislov.shop_front.repositiries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.dto.*;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.ShopClient;

import java.util.List;


@FeignClient(
        name = "shop-back-user",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface ShopFeign {
    @GetMapping("/shop/catalog/{id}")
    ResponseEntity<Product> product(@PathVariable("id") long id);

    @GetMapping("/shop/catalog")
    ResponseEntity<ProductListDTO> products(@RequestParam(value = "page", required = false) int page,
                                            @RequestParam(value = "size", required = false) int size,
                                            @RequestParam(value = "sortBy", required = false) String sortBy);

    @PostMapping("/shop/catalog/addToCart")
    ResponseEntity<Void> addToCart(@RequestBody CartItemsDTO dto);

    @PostMapping("/shop/catalog/updateQuantity")
    ResponseEntity<Void> updateQuantity(@RequestBody UpdateQuantityDTO dto);

    @PostMapping("/shop/catalog/deleteFromCart")
    ResponseEntity<Void> deleteFromCart(@RequestBody CartItemsDTO dto);

    @GetMapping("/client/exists-id")
    ResponseEntity<Boolean> existsById(@RequestParam("id") long id);

    @GetMapping("/client/exists-email")
    ResponseEntity<Boolean> existsByEmail(@RequestParam("email") String email);

    @PostMapping("/client/additionalInformation")
    ResponseEntity<?> createShopCart(@RequestBody ShopClient shopClient);

    @GetMapping("/shop/client/get-cart")
    ResponseEntity<List<ProductQuantityDTO>> getShopCart(@RequestParam("id") long id);

    @PostMapping("/order/make-order")
    ResponseEntity<Void> makeOrder(@RequestParam("clientId")long id);
}

