package com.kelompok.biodata.controller;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private ThemeSettingRepository themeRepo;

    @GetMapping("/")
    public String home(
            @AuthenticationPrincipal OAuth2User principal,
            Model model
    ) {
        ThemeSetting theme = themeRepo.findById(1L)
                .orElse(new ThemeSetting());
        model.addAttribute("theme", theme);

        if (principal != null) {
            model.addAttribute("userName",  principal.getAttribute("name"));
            model.addAttribute("userEmail", principal.getAttribute("email"));
            model.addAttribute("userPicture", principal.getAttribute("picture"));
        }

        return "index";
    }

    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal OAuth2User principal,
            Model model
    ) {
        model.addAttribute("userName",    principal.getAttribute("name"));
        model.addAttribute("userEmail",   principal.getAttribute("email"));
        model.addAttribute("userPicture", principal.getAttribute("picture"));
        model.addAttribute("userLocale",  principal.getAttribute("locale"));

        return "profile";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "index";
    }
}
