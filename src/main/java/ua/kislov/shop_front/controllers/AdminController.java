package ua.kislov.shop_front.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.services.interfaces.AdminServiceInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {

    private final AdminServiceInterface adminService;
    private final ModelMapper mapper;

    @Autowired
    public AdminController(AdminServiceInterface adminService, ModelMapper mapper) {
        this.adminService = adminService;
        this.mapper = mapper;
    }

    @GetMapping("/admin/users")
    public String users(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                                                      @RequestParam(value = "size", required = false,
                                                              defaultValue = "3") int size,
                                                      @RequestParam(value = "sortBy", required = false,
                                                              defaultValue = "username") String sortBy,
                                                      Model model) {
        SecurityShopClientListDTO listDTO = adminService.findByAll(pageNumber, size, sortBy);
        int totalPages = listDTO.getPageTotal();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("users", listDTO.getList());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        return "admin/users";
    }

    @GetMapping("/admin/users/{id}")
    public String user(@PathVariable("id") long id, Model model){
        model.addAttribute("user", adminService.findById(id));
        return "admin/user";
    }

    private List<SecurityShopClient> getUsers(Page<SecurityShopClientDTO> dtos){
        return dtos.getContent().stream().map(this::fromDTO).collect(Collectors.toList());
    }

    private SecurityShopClient fromDTO(SecurityShopClientDTO dto){
        return mapper.map(dto, SecurityShopClient.class);
    }
}
