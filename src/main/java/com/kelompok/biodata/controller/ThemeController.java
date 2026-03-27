package com.kelompok.biodata.controller;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeSettingRepository themeRepo;

    @GetMapping("/settings")
    @PreAuthorize("hasRole('MEMBER')")
    public String themeSettings(
            @AuthenticationPrincipal OAuth2User principal,
            Model model
    ) {
        ThemeSetting theme = themeRepo.findById(1L)
                .orElse(new ThemeSetting());
        model.addAttribute("theme", theme);
        model.addAttribute("userName", principal.getAttribute("name"));
        return "theme-settings";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('MEMBER')")
    public String updateTheme(
            @RequestParam String primaryColor,
            @RequestParam String backgroundColor,
            @RequestParam String fontFamily,
            @RequestParam String textColor,
            RedirectAttributes redirectAttrs
    ) {
        ThemeSetting theme = themeRepo.findById(1L)
                .orElse(new ThemeSetting());

        theme.setPrimaryColor(primaryColor);
        theme.setBackgroundColor(backgroundColor);
        theme.setFontFamily(fontFamily);
        theme.setTextColor(textColor);

        themeRepo.save(theme);

        redirectAttrs.addFlashAttribute("successMessage",
                "Tampilan berhasil diperbarui!");
        return "redirect:/theme/settings";
    }

    @PostMapping("/reset")
    @PreAuthorize("hasRole('MEMBER')")
    public String resetTheme(RedirectAttributes redirectAttrs) {
        ThemeSetting defaultTheme = new ThemeSetting();
        defaultTheme.setId(1L);
        themeRepo.save(defaultTheme);

        redirectAttrs.addFlashAttribute("successMessage",
                "Tampilan direset ke default.");
        return "redirect:/theme/settings";
    }
}
