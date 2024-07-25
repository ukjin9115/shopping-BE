package ukgo.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ukgo.shop.service.MemberService;
import ukgo.shop.service.MyUserDetailsService;
//로그인기능추가요
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(Authentication auth){
        if (auth.isAuthenticated()){
            return "redirect:/list";
        }
        return "signup.html";
    }

    @PostMapping("/register")
    public String register(String username, String password, String displayName) {
        memberService.saveMember(username, displayName, password); // Corrected the parameter order
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        System.out.println(auth);
        System.out.println(auth.getName()); //아이디출력가능
        System.out.println(auth.isAuthenticated()); //로그인여부 검사가능
        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));
        return "mypage.html";
    }
}
