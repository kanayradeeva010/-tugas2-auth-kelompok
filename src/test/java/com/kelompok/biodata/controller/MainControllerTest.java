package com.kelompok.biodata.controller;

import com.kelompok.biodata.model.ThemeSetting;
import com.kelompok.biodata.model.ThemeSettingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeSettingRepository themeRepo;
    ThemeSetting mockTheme = new ThemeSetting(); 

    @Test
    public void testHomePage_AnonymousUser() throws Exception {
        when(themeRepo.findById(1L)).thenReturn(Optional.of(mockTheme));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("isLoggedIn", false))
                .andExpect(model().attributeExists("theme"));
    }

    @Test
    public void testHomePage_AuthenticatedUser() throws Exception {
        ThemeSetting mockTheme = new ThemeSetting();
        when(themeRepo.findById(1L)).thenReturn(Optional.of(mockTheme));

        mockMvc.perform(get("/")
                .with(oauth2Login() // Simulasi login OAuth2
                        .attributes(attrs -> {
                            attrs.put("name", "Budi Santoso");
                            attrs.put("email", "budi@example.com");
                            attrs.put("picture", "http://image.com/budi.jpg");
                        })))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isLoggedIn", true))
                .andExpect(model().attribute("userName", "Budi Santoso"))
                .andExpect(model().attribute("userEmail", "budi@example.com"));
    }

    @Test
    public void testProfilePage_Authenticated() throws Exception {
        mockMvc.perform(get("/profile")
                .with(oauth2Login()
                        .attributes(attrs -> {
                            attrs.put("name", "Budi");
                            attrs.put("email", "budi@example.com");
                            attrs.put("picture", "pic.jpg");
                            attrs.put("locale", "id");
                        })))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("userName", "Budi"));
    }

    @Test
    public void testProfilePage_RedirectToLogin() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection());
    }
}