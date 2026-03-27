package com.kelompok.biodata.service;

import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class MemberService {

    private static final Set<String> MEMBER_EMAILS = Set.of(
            "namarabigail@gmail.com",
            "adeevakanayra@gmail.com",
            "fini.ardiyanti@gmail.com",
            "uzma.zhafira@gmail.com",
            "zita.nayra.ardini@gmail.com"
    );

    public boolean isMember(String email) {
        if (email == null) return false;
        return MEMBER_EMAILS.contains(email.toLowerCase());
    }

    public Set<String> getMemberEmails() {
        return MEMBER_EMAILS;
    }
}
