package com.kelompok.biodata.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private OAuth2UserRequest userRequest;

    @Mock
    private OAuth2User mockOAuth2User;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    @Test
    void testLoadUser_WhenEmailIsMember_ShouldAddRoleMember() {
        final String testEmail = "namarabigail@gmail.com";

        try (MockedConstruction<DefaultOAuth2UserService> mocked = mockConstruction(DefaultOAuth2UserService.class,
                (mock, context) -> {
                    when(mock.loadUser(userRequest)).thenReturn(mockOAuth2User);
                })) {

            when(mockOAuth2User.getAttribute("email")).thenReturn(testEmail);
            when(mockOAuth2User.getAttributes()).thenReturn(Map.of("email", testEmail, "sub", "12345"));
            when(memberService.isMember(testEmail)).thenReturn(true);

            OAuth2User result = customOAuth2UserService.loadUser(userRequest);

            Collection<? extends GrantedAuthority> authorities = result.getAuthorities();
            assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
            assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEMBER")));
        }
    }

    @Test
    void testLoadUser_WhenEmailIsNotMember_ShouldOnlyHaveRoleUser() {
        final String testEmail = "stranger@gmail.com";

        try (MockedConstruction<DefaultOAuth2UserService> mocked = mockConstruction(DefaultOAuth2UserService.class,
                (mock, context) -> {
                    when(mock.loadUser(userRequest)).thenReturn(mockOAuth2User);
                })) {

            when(mockOAuth2User.getAttribute("email")).thenReturn(testEmail);
            when(mockOAuth2User.getAttributes()).thenReturn(Map.of("email", testEmail, "sub", "67890"));
            when(memberService.isMember(testEmail)).thenReturn(false);

            OAuth2User result = customOAuth2UserService.loadUser(userRequest);

            Collection<? extends GrantedAuthority> authorities = result.getAuthorities();
            assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
            assertFalse(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEMBER")));
        }
    }
}