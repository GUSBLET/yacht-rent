package com.yachtrent.main.profile;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.account.dto.Profile;
import com.yachtrent.main.order.OrderService;
import com.yachtrent.main.order.dto.DateRange;
import com.yachtrent.main.order.dto.OrderDto;
import com.yachtrent.main.yacht.YachtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class ProfileController {
    private final AccountService accountService;
    private final OrderService orderService;
    private final YachtService yachtService;
    private Profile profile = new Profile();
    private List<Profile> profiles;
    private List<OrderDto> orders;

    @GetMapping
    String viewAccount(@RequestParam(name = "rememberMe", required = false) boolean rememberMe, Model model) {
        Account account = accountService.getAuthentication();
        account.setAccountRegistered(rememberMe);
        profile = new Profile().toDto(account);
        profile.setYachts(yachtService.getYachts(account));
        profile.setOrders(orderService.getOrders(account));

        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "order")
                .addAttribute("account", profile);
        return "layout";
    }

    @GetMapping("/profile-order")
    String profileOrderPage(Model model) {
        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "order")
                .addAttribute("account", profile)
                .addAttribute("accountId", profile.getId())
                .addAttribute("orders", profile.getOrders());
        return "layout";
    }

    @GetMapping("/profile-yacht")
    String profileYachtPage(Model model) {
        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "yacht")
                .addAttribute("account", profile)
                .addAttribute("accountId", profile.getId())
                .addAttribute("yachts", profile.getYachts());
        return "layout";
    }

    @GetMapping("/all_user")
    String adminProfile(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model
    ) {
        if (profiles == null) {
            profiles = accountService.findAllProfile();
        }

        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "all users")
                .addAttribute("account", profile)
                .addAttribute("accountId", profile.getId())
                .addAttribute("users", accountService.getPageProfile(
                        profiles, PageRequest.of(page, size))
                );
        return "layout";
    }

    @PostMapping("/search_user")
    String findProfilesByName(@RequestParam("searchName") String searchName) {
        if (searchName.isBlank()) {
            log.warn("Did not specify a name to search for yachts");
            profiles = null;
            return "redirect:/cabinet/all_user";
        }
        profiles = accountService.findAccountByName(searchName);
        return "redirect:/cabinet/all_user";
    }

    @PostMapping("/blocked/{id}")
    String changeStatusBlocked(@PathVariable long id, @RequestParam(value = "isBlocked") boolean isBlocked) {
        accountService.changeStatusAccountBlocked(id, !isBlocked);
        profiles = accountService.findAllProfile();
        return "redirect:/cabinet/all_user";
    }

    @PostMapping("/role_distribution/{id}")
    String roleDistribution(@PathVariable long id) {
        accountService.updateRoleAccount(id, "YACHT OWNER");
        profiles = accountService.findAllProfile();
        return "redirect:/cabinet/all_user";
    }

    @PostMapping("/update_role")
    String updateRoleAccount(@RequestParam long id, @RequestParam String role) {
        accountService.updateRoleAccount(id, role);

        return "redirect:/cabinet/all_user";
    }

    @GetMapping("/all_orders")
    String getAllOrders(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model
    ) {
        if (orders == null) {
            orders = orderService.findAllOrder();
        }

        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "all orders")
                .addAttribute("account", profile)
                .addAttribute("accountId", profile.getId())
                .addAttribute("range", new DateRange())
                .addAttribute("orders", orderService.getPageOrders(
                        orders, PageRequest.of(page, size))
                );
        return "layout";
    }

    @PostMapping("/dateRange")
    String getDateRange(@RequestParam("datemin") String datemin, @RequestParam("datemax") String datemax) {
        orders = orderService.findOrdersByDateRange(datemin, datemax);
        return "redirect:/cabinet/all_orders";
    }

    @PostMapping("/show-all-orders")
    String getDateRange() {
        log.warn("Show all orders");
        orders = null;
        return "redirect:/cabinet/all_orders";
    }
}
