package axsoup.web;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import axsoup.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import axsoup.Order;
import axsoup.User;
import axsoup.data.OrderRepository;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@ConfigurationProperties(prefix="axsoup.orders")
public class OrderController {

    private int pageSize = 20;
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

@Autowired
    private OrderRepository orderRepo;

    private UserRepository userRepo;

    public OrderController(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
                               Principal principal) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        User user = userRepo.findByUsername(
                principal.getName());
        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        // Ограничиваем кол-во выводимых заказов
        Pageable pageable = PageRequest.of(0, 20);
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user));
        return "orderList";
    }

}
