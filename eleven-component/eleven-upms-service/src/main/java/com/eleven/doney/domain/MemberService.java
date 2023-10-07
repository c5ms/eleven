package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.domain.DomainSupport;
import com.eleven.doney.core.DoneyConstants;
import com.eleven.doney.dto.MemberDto;
import com.eleven.doney.dto.MemberSaveAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberConvertor memberConvertor;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final DomainSupport domainSupport;

    public Optional<MemberDto> getMember(String id) {
        var membership = memberRepository.findById(id);
        return membership.map(memberConvertor::toDto);
    }

    public MemberDto updateMember(String id, MemberSaveAction action) {
        var membership = memberRepository.requireById(id);
        membership.update(action);
        memberRepository.save(membership);
        return memberConvertor.toDto(membership);
    }

    public void deleteMember(String id) {
        var membership = memberRepository.requireById(id);
        memberRepository.delete(membership);
    }

    public Optional<List<MemberDto>> listMembersOfProject(String pid) {
        return projectRepository.findById(pid).map(project -> {
            var sort = Sort.by(AbstractAuditableDomain.Fields.createAt).descending();
            return memberRepository.findByProjectId(pid, sort)
                    .stream()
                    .map(memberConvertor::toDto)
                    .collect(Collectors.toList());
        });
    }

    public Optional<MemberDto> addMemberToProject(String pid, MemberSaveAction action) {
        if (memberRepository.existsByProjectIdAndUserId(pid, action.getUserId())) {
            throw DoneyConstants.ERROR_MEMBER_EXIST.exception();
        }
        return projectRepository.findById(pid).map(project -> {
            var memberId = domainSupport.nextId();
            var membership = new Member(memberId, pid, action);
            memberRepository.save(membership);
            return memberConvertor.toDto(membership);
        });
    }

}
