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
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "项目成员")
@RequestMapping("/members")
@AsRestApi
@RequiredArgsConstructor
public class MemberRestApi {

    private final MemberService memberService;

    @Operation(summary = "项目成员读取")
    @GetMapping("/{id}")
    public MemberDto getMembership(@PathVariable("id") String id) {
        return memberService.getMember(id).orElseThrow(DataNotFoundException::new);
    }

    @Operation(summary = "项目成员更新")
    @PutMapping("/{id}")
    public MemberDto updateMembership(@PathVariable("id") String id, @RequestBody MemberSaveAction action) {
        return memberService.updateMember(id, action);
    }

    @Operation(summary = "项目成员删除")
    @DeleteMapping("/{id}")
    public void deleteMembership(@PathVariable("id") String id) {
        memberService.deleteMember(id);
    }


}
