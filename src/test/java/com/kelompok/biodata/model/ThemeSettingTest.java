package com.kelompok.biodata.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThemeSettingTest {

    @Test
    void testDefaultValues() {
        ThemeSetting theme = new ThemeSetting();
        
        assertEquals(1L, theme.getId());
        assertEquals("#2563EB", theme.getPrimaryColor());
        assertEquals("#F8FAFC", theme.getBackgroundColor());
        assertEquals("'Segoe UI', sans-serif", theme.getFontFamily());
        assertEquals("#1E293B", theme.getTextColor());
    }

    @Test
    void testSettersAndGetters() {
        ThemeSetting theme = new ThemeSetting();        
        Long newId = 99L;

        theme.setId(newId);
        theme.setPrimaryColor("#000000");
        theme.setBackgroundColor("#FFFFFF");
        theme.setFontFamily("Arial");
        theme.setTextColor("#333333");

        assertEquals(newId, theme.getId());
        assertEquals("#000000", theme.getPrimaryColor());
        assertEquals("#FFFFFF", theme.getBackgroundColor());
        assertEquals("Arial", theme.getFontFamily());
        assertEquals("#333333", theme.getTextColor());
    }
}