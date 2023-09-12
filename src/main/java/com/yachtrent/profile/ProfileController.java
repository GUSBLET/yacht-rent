package com.yachtrent.profile;

import com.yachtrent.main.account.Account;
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
        model.addAttribute("account", account);
        account.setAccountRegistered(rememberMe);
        return "account/profile";
    }

}