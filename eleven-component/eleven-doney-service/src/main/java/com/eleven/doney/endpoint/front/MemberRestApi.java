package com.eleven.doney.endpoint.front;

import com.eleven.core.exception.DataNotFoundException;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.doney.domain.MemberService;
import com.eleven.doney.model.MemberDto;
import com.eleven.doney.model.MemberSaveAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "member")
@RequestMapping("/members")
@AsRestApi
@RequiredArgsConstructor
public class MemberRestApi {

    private final MemberService memberService;

    @Operation(summary = "detail")
    @GetMapping("/{id}")
    public MemberDto getMembership(@PathVariable("id") String id) {
        return memberService.getMember(id).orElseThrow(DataNotFoundException::new);
    }

    @Operation(summary = "update")
    @PutMapping("/{id}")
    public MemberDto updateMembership(@PathVariable("id") String id, @RequestBody @Validated MemberSaveAction action) {
        return memberService.updateMember(id, action);
    }

    @Operation(summary = "delete")
    @DeleteMapping("/{id}")
    public void deleteMembership(@PathVariable("id") String id) {
        memberService.deleteMember(id);
    }


}
