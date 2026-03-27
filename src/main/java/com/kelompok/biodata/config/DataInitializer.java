package com.kelompok.biodata.config;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ThemeSettingRepository themeRepo;

    @Override
    public void run(String... args) {
        if (!themeRepo.existsById(1L)) {
            ThemeSetting defaultTheme = new ThemeSetting();
            defaultTheme.setId(1L);
            themeRepo.save(defaultTheme);
            System.out.println("✅ Theme default berhasil dibuat");
        }
    }
}