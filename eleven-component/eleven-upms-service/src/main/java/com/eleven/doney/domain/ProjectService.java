package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditableDomain;
import com.eleven.core.domain.DomainSupport;
import com.eleven.core.domain.PaginationResult;
import com.eleven.doney.core.DoneyConstants;
import com.eleven.doney.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectConvertor projectConvertor;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final MemberConvertor memberConvertor;
    private final DomainSupport domainSupport;

    public Optional<ProjectDto> getProject(String id) {
        var project = projectRepository.findById(id);
        return project.map(projectConvertor::toDto);
    }

    public ProjectDto createProject(ProjectSaveAction action) {
        if (projectRepository.existsByCode(action.getCode())) {
            throw DoneyConstants.ERROR_PROJECT_CODE_EXIST.exception();
        }
        var id = domainSupport.nextId();
        var project = new Project(id);
        project.initial(action);
        projectRepository.save(project);
        return projectConvertor.toDto(project);
    }

    public ProjectDto updateProject(String id, ProjectSaveAction action) {
        var project = projectRepository.requireById(id);
        project.update(action);
        projectRepository.save(project);
        return projectConvertor.toDto(project);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProject(String id) {
        var project = projectRepository.requireById(id);
        project.delete();
        // TODO do we need delete member and role related to this project?
        // no,we delete project by soft flag and reserved all it's properties.
        projectRepository.save(project);
    }

    public PaginationResult<ProjectDto> queryProjectPage(ProjectQuery filter) {
        var criteria = Criteria.empty();

        // not deleted
        criteria = criteria.and(Criteria.where(Project.Fields.deleteAt).isNull());

        if (StringUtils.isNotBlank(filter.getTitle())) {
            criteria = criteria.and(Criteria.where(Project.Fields.title).like(StringUtils.trim(filter.getTitle()) + "%"));
        }

        var sort = Sort.by(AbstractAuditableDomain.Fields.createAt).descending();
        var query = Query.query(criteria).sort(sort);
        var page = domainSupport.queryPage(query, Project.class, filter.getPage(), filter.getSize());
        return page.map(projectConvertor::toDto);
    }

    public List<ProjectDto> listProjects() {
        var sort = Sort.by(AbstractAuditableDomain.Fields.createAt).descending();
        var query = Query.query(Criteria.empty()).sort(sort);

        return domainSupport.query(query, Project.class)
                .stream()
                .map(projectConvertor::toDto)
                .collect(Collectors.toList());
    }


}
