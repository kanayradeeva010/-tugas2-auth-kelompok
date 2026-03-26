package com.kelompok.biodata.controller;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/theme-settings")
public class ThemeController {

    @Autowired
    private ThemeSettingRepository themeSettingRepository;

    @GetMapping
    public String themeSettings(Model model) {
        model.addAttribute("themes", themeSettingRepository.findAll());
        model.addAttribute("newTheme", new ThemeSetting());
        return "theme-settings";
    }

    @PostMapping
    public String saveTheme(@ModelAttribute ThemeSetting themeSetting) {
        themeSettingRepository.save(themeSetting);
        return "redirect:/theme-settings";
    }
}