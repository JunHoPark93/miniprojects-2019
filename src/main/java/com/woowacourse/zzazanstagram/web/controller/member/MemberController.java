package com.woowacourse.zzazanstagram.web.controller.member;

import com.woowacourse.zzazanstagram.model.member.dto.MemberResponse;
import com.woowacourse.zzazanstagram.model.member.dto.MemberSignUpRequest;
import com.woowacourse.zzazanstagram.model.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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

    @GetMapping("/members/{memberId}/edit")
    public String editMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        MemberResponse response = memberService.find(memberId);
        model.addAttribute("member", response);
        return "member-edit";
    }

    @PutMapping("/members/{memberId}/edit")
    public String editMember(@PathVariable("memberId") Long memberId,  @Valid MemberSignUpRequest memberSignUpRequest) {
        memberService.edit(memberId, memberSignUpRequest);
        return "redirect:/";
    }
}
