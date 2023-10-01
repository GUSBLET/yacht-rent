package com.yachtrent.main.profile;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderRepository;
import com.yachtrent.main.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

@Slf4j
@Controller
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class ProfileController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("/success")
    public String getSuccessPage(@AuthenticationPrincipal Account account,
                                 @RequestParam(name = "rememberMe", required = false) boolean rememberMe,
                                 Model model
    ) {
        LinkedList<Order> orderList = new LinkedList<>();
//        orderList.add(
//                Order.builder()
//                        .price(1000F)
//                        .startOfRent(new Date())
//                        .finishOfRent(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000L))
//                        .confirmedOrder(true)
//                        .build());

        model.addAttribute("account", account)
                .addAttribute("orders", orderList);
        account.setAccountRegistered(rememberMe);
        return "account/profile";
    }




    @GetMapping("/profile-order")
    public String profileOrderPage(Model model){
        model.addAttribute("title", "Profile");
        model.addAttribute("content", "profile");
        model.addAttribute("profileContent", "order");

        return "layout";
    }

    @GetMapping("/profile-review")
    public String profileReviewPage(Model model){
        model.addAttribute("title", "Profile");
        model.addAttribute("content", "profile");
        model.addAttribute("profileContent", "review");

        return "layout";
    }

    @GetMapping("/profile-yacht")
    public String profileYachtPage(Model model){
        model.addAttribute("title", "Profile");
        model.addAttribute("content", "profile");
        model.addAttribute("profileContent", "yacht");

        return "layout";
    }




}