package com.eleven.doney.domain;

import com.eleven.core.domain.AbstractAuditDomain;
import com.eleven.core.domain.DomainSupport;
import com.eleven.core.domain.PaginationResult;
import com.eleven.doney.model.IssueDto;
import com.eleven.doney.model.IssueQuery;
import com.eleven.doney.model.IssueSaveAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueConvertor issueConvertor;
    private final IssueRepository issueRepository;
    private final DomainSupport domainSupport;

    public Optional<IssueDto> getIssue(String id) {
        var task = issueRepository.findById(id);
        return task.map(issueConvertor::toDto);
    }

    public IssueDto createIssue(IssueSaveAction action) {
        var id = domainSupport.getNextId();
        var issue = new Issue(id,action);
        issueRepository.save(issue);
        return issueConvertor.toDto(issue);
    }

    public IssueDto updateIssue(String id, IssueSaveAction action) {
        var issue = issueRepository.requireById(id);
        issue.update(action);
        issueRepository.save(issue);
        return issueConvertor.toDto(issue);
    }

    public void deleteIssue(String id) {
        var task = issueRepository.requireById(id);
        issueRepository.delete(task);
    }


    public PaginationResult<IssueDto> queryIssuePage(IssueQuery filter) {
        var criteria = Criteria.empty();
        if (StringUtils.isNotBlank(filter.getTitle())) {
            criteria = criteria.and(Criteria.where(Issue.Fields.title).like("%" + StringUtils.trim(filter.getTitle()) + "%"));
        }
        if (StringUtils.isNotBlank(filter.getProjectId())) {
            criteria = criteria.and(Criteria.where(Issue.Fields.projectId).is(filter.getProjectId()));
        }

        var sort = Sort.by(AbstractAuditDomain.Fields.createAt).descending();
        var query = Query.query(criteria).sort(sort);
        var page = domainSupport.queryPage(query, Issue.class, filter.getPage(), filter.getSize());
        return page.map(issueConvertor::toDto);
    }


}
