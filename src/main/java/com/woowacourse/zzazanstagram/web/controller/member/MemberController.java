package com.woowacourse.zzazanstagram.web.controller.member;

import com.woowacourse.zzazanstagram.model.member.dto.MemberResponse;
import com.woowacourse.zzazanstagram.model.member.dto.MemberSignUpRequest;
import com.woowacourse.zzazanstagram.model.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final Map<String, MemberResponse> sessionMap;

    public MemberController(MemberService memberService, Map<String, MemberResponse> sessionMap) {
        this.memberService = memberService;
        this.sessionMap = sessionMap;
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/members")
    public String saveMember(@Valid MemberSignUpRequest memberSignupRequest) {
        memberService.save(memberSignupRequest);
        return "redirect:/login";
    }
}
