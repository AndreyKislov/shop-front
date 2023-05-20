package ua.kislov.shop_front.repositiries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.dto.OrdersDTO;
import ua.kislov.shop_front.dto.ProductListDTO;
import ua.kislov.shop_front.dto.ShopClientDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.ShopOrder;

@FeignClient(
        name = "shop-back-admin",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface AdminShopFeign {


    @GetMapping("/admin/client/{id}")
    ResponseEntity<ShopClientDTO> shopClient(@PathVariable("id") long id);

    @DeleteMapping("/admin/client/{id}")
    ResponseEntity<Void> deleteShopClient(@PathVariable("id") long id);

    @GetMapping("/admin/product_isExists")
    ResponseEntity<Boolean> productIsExists(@RequestParam(name = "name") String name);

    @PostMapping("/admin/new_product")
    ResponseEntity<Void> saveNewProduct(@RequestBody Product product);

    @GetMapping("/admin/orders")
    ResponseEntity<String> getOrders();

    @GetMapping("/admin/order")
    ResponseEntity<ShopOrder> getOrder(@RequestParam("id") long id);

    @PostMapping("/admin/order")
    ResponseEntity<Void> completedOrder(@RequestParam("id") long id);

    @PostMapping("/admin/delete-product")
    ResponseEntity<Void> deleteProduct(@RequestParam("id") long id);
}
