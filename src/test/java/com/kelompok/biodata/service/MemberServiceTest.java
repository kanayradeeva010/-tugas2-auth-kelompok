package com.kelompok.biodata.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService();
    }

    @Test
    @DisplayName("Harus mengembalikan true untuk email member yang valid")
    void testIsMember_ValidEmail_ReturnsTrue() {
        assertTrue(memberService.isMember("namarabigail@gmail.com"));
        assertTrue(memberService.isMember("uzma.zhafira@gmail.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NAMARABIGAIL@GMAIL.COM", "Uzma.Zhafira@Gmail.Com", "FINI.ardiyanti@gmail.com"})
    @DisplayName("Harus case-insensitive (huruf besar/kecil tidak berpengaruh)")
    void testIsMember_CaseInsensitive_ReturnsTrue(String email) {
        assertTrue(memberService.isMember(email), "Harusnya true meskipun case berbeda untuk: " + email);
    }

    @Test
    @DisplayName("Harus mengembalikan false untuk email yang bukan member")
    void testIsMember_InvalidEmail_ReturnsFalse() {
        assertFalse(memberService.isMember("bukan.member@ui.ac.id"));
        assertFalse(memberService.isMember("stranger@gmail.com"));
    }

    @Test
    @DisplayName("Harus mengembalikan false jika email null")
    void testIsMember_NullEmail_ReturnsFalse() {
        assertFalse(memberService.isMember(null));
    }

    @Test
    @DisplayName("Harus mengembalikan semua daftar email member dengan benar")
    void testGetMemberEmails() {
        Set<String> emails = memberService.getMemberEmails();
        
        assertNotNull(emails);
        assertEquals(5, emails.size(), "Jumlah member harus tepat 5");
        assertTrue(emails.contains("namarabigail@gmail.com"));
        assertTrue(emails.contains("adeevakanayra@gmail.com"));
    }
}