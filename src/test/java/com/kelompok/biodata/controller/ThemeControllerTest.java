package com.kelompok.biodata.controller;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeSettingRepository themeRepo;

    @Test
    public void testThemeSettings_AuthenticatedAsMember() throws Exception {
        ThemeSetting mockTheme = new ThemeSetting();
        when(themeRepo.findById(1L)).thenReturn(Optional.of(mockTheme));

        mockMvc.perform(get("/theme/settings")
                .with(oauth2Login()
                        .authorities(() -> "ROLE_MEMBER") // Simulasi role MEMBER
                        .attributes(attrs -> attrs.put("name", "Gem User"))))
                .andExpect(status().isOk())
                .andExpect(view().name("theme-settings"))
                .andExpect(model().attributeExists("theme"))
                .andExpect(model().attribute("userName", "Gem User"));
    }

    @Test
    public void testThemeSettings_ForbiddenForNonMember() throws Exception {
        // User login tapi tidak punya role MEMBER (default biasanya ROLE_USER)
        mockMvc.perform(get("/theme/settings")
                .with(oauth2Login())) 
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateTheme_Success() throws Exception {
        ThemeSetting existingTheme = new ThemeSetting();
        when(themeRepo.findById(1L)).thenReturn(Optional.of(existingTheme));
        when(themeRepo.save(any(ThemeSetting.class))).thenReturn(existingTheme);

        clearInvocations(themeRepo);

        mockMvc.perform(post("/theme/update")
                        .param("primaryColor", "#FF0000")
                        .param("backgroundColor", "#FFFFFF")
                        .param("fontFamily", "Arial")
                        .param("textColor", "#000000")
                        .with(csrf())
                        .with(oauth2Login().authorities(
                                new SimpleGrantedAuthority("ROLE_MEMBER"),
                                new SimpleGrantedAuthority("ROLE_USER")
                        )))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(themeRepo, times(1)).save(any(ThemeSetting.class));
    }

    @Test
    public void testResetTheme_Success() throws Exception {
        mockMvc.perform(post("/theme/reset")
                .with(csrf())
                .with(oauth2Login().authorities(() -> "ROLE_MEMBER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/theme/settings"))
                .andExpect(flash().attribute("successMessage", "Tampilan direset ke default."));

        verify(themeRepo, times(1)).save(any(ThemeSetting.class));
    }
}