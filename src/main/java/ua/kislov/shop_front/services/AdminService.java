package ua.kislov.shop_front.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.dto.ShopClientDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.repositiries.AdminShopFeign;
import ua.kislov.shop_front.repositiries.AdminUserFeign;
import ua.kislov.shop_front.repositiries.ShopFeign;
import ua.kislov.shop_front.services.interfaces.AdminServiceInterface;

@Service
public class AdminService implements AdminServiceInterface {

    private final AdminUserFeign adminUserFeign;
    private final AdminShopFeign adminShopFeign;

    private final ShopFeign shopFeign;
    private final ModelMapper mapper;

    @Autowired
    public AdminService(AdminUserFeign adminUserFeign, AdminShopFeign adminShopFeign, ShopFeign shopFeign, ModelMapper mapper) {
        this.adminUserFeign = adminUserFeign;
        this.adminShopFeign = adminShopFeign;
        this.shopFeign = shopFeign;
        this.mapper = mapper;
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

    private SecurityShopClient fromDTOForSecurityShopClient(SecurityShopClientDTO dto){
        return mapper.map(dto, SecurityShopClient.class);
    }
    private ShopClient fromDTOForShopClient(ShopClientDTO dto){
        return mapper.map(dto, ShopClient.class);
    }
}
