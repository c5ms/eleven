package com.eleven.doney.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.eleven.doney.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectConvertor {

    private final ModelMapper modelMapper;

    public ProjectDto toDto(Project project) {
        var bean = modelMapper.map(project, ProjectDto.class);
        bean.setNewIssueAlarm(RandomUtil.randomInt(0,3));
        return bean;
    }


}
