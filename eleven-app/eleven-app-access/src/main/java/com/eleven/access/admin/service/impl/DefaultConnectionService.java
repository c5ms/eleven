package com.eleven.access.admin.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.access.admin.domain.action.ConnectionDefinitionQueryAction;
import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.access.admin.domain.event.ConnectionUpdatedEvent;
import com.cnetong.access.admin.domain.repository.ConnectionDefinitionRepository;
import com.cnetong.access.admin.service.ConnectionService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultConnectionService implements ConnectionService {
    private final ConnectionDefinitionRepository connectionDefinitionRepository;

    @Override
    public ResourceDefinition require(String id) {
        return connectionDefinitionRepository.findById(id).orElseThrow(() -> ProcessRejectException.of("连接不存在"));
    }

    @Override
    public Optional<ResourceDefinition> get(String id) {
        return connectionDefinitionRepository.findById(id);
    }

    @Override
    public ResourceDefinition save(ResourceDefinition resourceDefinition) {
        ResourceDefinition exist;
        if (StringUtils.isNotBlank(resourceDefinition.getId())) {
            exist = connectionDefinitionRepository.getReferenceById(resourceDefinition.getId());
            exist.update(resourceDefinition);
            exist = connectionDefinitionRepository.save(exist);
        } else {
            exist = connectionDefinitionRepository.save(resourceDefinition);
        }
        SpringUtil.publishEvent(new ConnectionUpdatedEvent(resourceDefinition.getId()));
        return exist;
    }

    @Override
    public void update(ResourceDefinition resourceDefinition) {
        connectionDefinitionRepository.save(resourceDefinition);
        SpringUtil.publishEvent(new ConnectionUpdatedEvent(resourceDefinition.getId()));
    }

    @Override
    public Page<ResourceDefinition> query(ConnectionDefinitionQueryAction action) {
        var page = connectionDefinitionRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public void delete(ResourceDefinition resourceDefinition) {
        connectionDefinitionRepository.delete(resourceDefinition);
        SpringUtil.publishEvent(new ConnectionUpdatedEvent(resourceDefinition.getId()));
    }

    @Override
    public Collection<ResourceDefinition> list() {
        return connectionDefinitionRepository.findAll();
    }

    @Override
    public Collection<ResourceDefinition> list(ConnectionDefinitionQueryAction action) {
        return connectionDefinitionRepository.findAll(action.toSpecification());
    }


}
