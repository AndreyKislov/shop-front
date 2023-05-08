package ua.kislov.shop_front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.dto.OrderDTO;
import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.services.ClientService;
import ua.kislov.shop_front.services.interfaces.OrderInterface;

import java.util.List;

@Controller
public class OrderController {

    private final OrderInterface orderService;
    private final ClientService clientService;

    @Autowired
    public OrderController(OrderInterface orderService, ClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @PostMapping("/order")
    public String makeOrder() {
        long clientId = clientService.getCurrentClientId();
        orderService.makeOrder(clientId);

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String successfulOrder() {
        return "/shop/order";
    }
}
