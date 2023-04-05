package ua.kislov.shop_front.security.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.ShopClientDTO;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.security.repositories.SecurityFeignClient;

@Service
public class SecurityClientDetailsService implements UserDetailsService {

    private final SecurityFeignClient securityFeignClient;
    private final ModelMapper mapper;

    @Autowired
    public SecurityClientDetailsService(SecurityFeignClient securityFeignClient, ModelMapper mapper) {
        this.securityFeignClient = securityFeignClient;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //получаем по username со стороннего сервиса (аутентификация и регистрация),
        // если юзера не существует то будет выброшено UsernameNotFoundException и
        // обработается далее по цепочке в AuthProvider в методе authenticate(Authentication authentication)
        ShopClientDTO dto = securityFeignClient.findByUsername(username);
        return new ClientDetails(convertToShopClient(dto));
    }

    private ShopClient convertToShopClient(ShopClientDTO dto) {
        return mapper.map(dto, ShopClient.class);
    }

}
