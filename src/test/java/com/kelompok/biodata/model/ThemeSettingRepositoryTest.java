package com.kelompok.biodata.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ThemeSettingRepositoryTest {

    @Autowired
    private ThemeSettingRepository repository;

    @Test
    void testSaveAndFind() {
        ThemeSetting theme = new ThemeSetting();
        theme.setPrimaryColor("#FF5733");
        
        repository.save(theme);
        
        Optional<ThemeSetting> result = repository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("#FF5733", result.get().getPrimaryColor());
    }
}