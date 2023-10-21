package com.eleven.doney.domain;

import cn.hutool.core.util.RandomUtil;
import com.eleven.doney.model.IssueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IssueConvertor {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    public IssueDto toDto(Issue domain) {
        var issue = modelMapper.map(domain, IssueDto.class);

        // 项目信息
        projectRepository.findSummaryById(domain.getProjectId()).ifPresent(issue::setProject);

        // 进度信息
        issue.setProgress(RandomUtil.randomInt(150));
        return issue;
    }

}
