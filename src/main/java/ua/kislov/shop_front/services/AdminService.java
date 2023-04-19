package ua.kislov.shop_front.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.repositiries.AdminUserFeign;
import ua.kislov.shop_front.services.interfaces.AdminServiceInterface;

@Service
public class AdminService implements AdminServiceInterface {

    private final AdminUserFeign adminUserFeign;
    private final ModelMapper mapper;

    @Autowired
    public AdminService(AdminUserFeign adminUserFeign, ModelMapper mapper) {
        this.adminUserFeign = adminUserFeign;
        this.mapper = mapper;
    }


    @Override
    public SecurityShopClientListDTO findByAll(int page, int size, String sort) {
        return adminUserFeign.users(page, size, sort).getBody();
    }

    @Override
    public SecurityShopClient findById(long id) {
        return fromDTO(adminUserFeign.user(id).getBody());
    }

    private SecurityShopClient fromDTO(SecurityShopClientDTO dto){
        return mapper.map(dto, SecurityShopClient.class);
    }
}
