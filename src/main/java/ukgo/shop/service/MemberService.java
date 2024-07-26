package ukgo.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ukgo.shop.entity.Member;
import ukgo.shop.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(String username, String displayName, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        validateUsername(username);
        validatePassword(password);

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(encodedPassword);
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    private void validateUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("패스워드가 너무 짧음");
        }
    }
}
