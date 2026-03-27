package com.kelompok.biodata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final Logger log = Logger.getLogger(CustomOAuth2UserService.class.getName());

    @Autowired
    private MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name  = oAuth2User.getAttribute("name");
        log.info("User login attempt: " + email);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (memberService.isMember(email)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            log.info("User " + email + " dikenali sebagai anggota kelompok → ROLE_MEMBER");
        } else {
            log.info("User " + email + " bukan anggota kelompok → ROLE_USER only");
        }

        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "sub"
        );
    }
}
