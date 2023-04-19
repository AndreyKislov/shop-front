package ua.kislov.shop_front.repositiries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.SecurityShopClient;

import java.util.List;

@FeignClient(
        name = "auth-reg-service-admin",
        url = "${auth-reg-service.url}",
        configuration = FeignConfig.class)
public interface AdminUserFeign {

    @GetMapping("/admin/users")
    ResponseEntity<SecurityShopClientListDTO> users(@RequestParam(value = "page", required = false) int page,
                                                    @RequestParam(value = "size", required = false) int size,
                                                    @RequestParam(value = "sortBy", required = false) String sortBy);

    @GetMapping("/admin/users/{id}")
    ResponseEntity<SecurityShopClientDTO> user(@PathVariable("id") long id);

}
