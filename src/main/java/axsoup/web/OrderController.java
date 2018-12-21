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

public class OrderController {

//    private int pageSize = 20;
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }

//    @Autowired
    private OrderRepository orderRepo;

//    private UserRepository userRepo;

    private OrderProps props;

    public OrderController(OrderRepository orderRepo, UserRepository userRepo, OrderProps props) {
        this.orderRepo = orderRepo;
//        this.userRepo = userRepo;
        this.props = props;

    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
//                               Principal principal)
                               @AuthenticationPrincipal User user){
        if (errors.hasErrors()) {
            return "orderForm";
        }

//        User user = userRepo.findByUsername(
//                principal.getName());
        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    // Ограничиваем кол-во выводимых заказов
    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

}
