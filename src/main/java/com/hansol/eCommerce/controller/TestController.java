package com.hansol.eCommerce.controller;

import com.hansol.eCommerce.dto.MemberFormDto;
import com.hansol.eCommerce.entity.Member;
import com.hansol.eCommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/test/new")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberFormDto memberFormDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid input");
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Member created successfully");
    }

    @GetMapping("/test/new")
    public ResponseEntity<MemberFormDto> newMemberForm() {
        return ResponseEntity.ok(new MemberFormDto());
    }
}
