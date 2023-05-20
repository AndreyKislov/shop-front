package ua.kislov.shop_front.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.OrdersDTO;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.dto.ShopClientDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.models.ShopOrder;
import ua.kislov.shop_front.repositiries.AdminShopFeign;
import ua.kislov.shop_front.repositiries.AdminUserFeign;
import ua.kislov.shop_front.services.interfaces.AdminInterface;

@Service
public class Admin implements AdminInterface {

    private final AdminUserFeign adminUserFeign;
    private final AdminShopFeign adminShopFeign;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public Admin(AdminUserFeign adminUserFeign, AdminShopFeign adminShopFeign, ModelMapper mapper, ObjectMapper objectMapper) {
        this.adminUserFeign = adminUserFeign;
        this.adminShopFeign = adminShopFeign;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }


    @Override
    public SecurityShopClientListDTO findSecurityShopClientByAll(int page, int size, String sort) {
        return adminUserFeign.users(page, size, sort).getBody();
    }

    @Override
    public SecurityShopClient findSecurityShopClientById(long id) {
        return fromDTOForSecurityShopClient(adminUserFeign.user(id).getBody());
    }

    public ShopClient findShopClientById(long id){
        return fromDTOForShopClient(adminShopFeign.shopClient(id).getBody());
    }

    @Override
    public void deleteUserAndClient(long id) {
        adminUserFeign.deleteUser(id);
        adminShopFeign.deleteShopClient(id);
    }

    @Override
    public Boolean productIsExists(String name) {
        return adminShopFeign.productIsExists(name).getBody();
    }

    @Override
    public void saveNewProduct(Product product) {
        adminShopFeign.saveNewProduct(product);
    }

    @Override
    public OrdersDTO getOrders() throws JsonProcessingException {
        String json = adminShopFeign.getOrders().getBody();
        return objectMapper.readValue(json, OrdersDTO.class);
    }

    @Override
    public ShopOrder getOrder(long id) {
        return adminShopFeign.getOrder(id).getBody();
    }

    @Override
    public void completedOrder(long id) {
        adminShopFeign.completedOrder(id);
    }

    @Override
    public void deleteProduct(long id) {
        adminShopFeign.deleteProduct(id);
    }

    private SecurityShopClient fromDTOForSecurityShopClient(SecurityShopClientDTO dto){
        return mapper.map(dto, SecurityShopClient.class);
    }
    private ShopClient fromDTOForShopClient(ShopClientDTO dto){
        return mapper.map(dto, ShopClient.class);
    }
}
