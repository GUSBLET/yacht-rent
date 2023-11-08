package com.yachtrent.main.profile;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.dto.Profile;
import com.yachtrent.main.yacht.YachtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final YachtService yachtService;
    private Profile profile;

    @GetMapping
    public String viewAccount(@RequestParam(name = "rememberMe", required = false) boolean rememberMe, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) auth.getPrincipal();
        account.setAccountRegistered(rememberMe);

        profile = new Profile().toDto(account);
        profile.setYachts(yachtService.getYachts(account));

        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "order")
                .addAttribute("account", profile);

        return "layout";
    }

    @GetMapping("/profile-order")
    public String profileOrderPage(Model model) {
        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "order")
                .addAttribute("account", profile);

        return "layout";
    }

    @GetMapping("/profile-review")
    public String profileReviewPage(Model model) {
        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "review")
                .addAttribute("account", profile);

        return "layout";
    }

    @GetMapping("/profile-yacht")
    public String profileYachtPage(Model model) {

        model.addAttribute("title", "Profile")
                .addAttribute("content", "profile")
                .addAttribute("profileContent", "yacht")
                .addAttribute("account", profile)
                .addAttribute("accountId", profile.getId())
                .addAttribute("yachts", profile.getYachts());

        return "layout";
    }
}