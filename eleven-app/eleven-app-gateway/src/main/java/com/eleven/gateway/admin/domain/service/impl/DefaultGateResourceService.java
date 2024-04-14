package com.eleven.gateway.admin.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.basic.service.SerialGenerator;
import com.cnetong.common.persist.jpa.helper.JpaLobHelper;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.action.GateResourceQueryAction;
import com.eleven.gateway.admin.domain.entity.GateResource;
import com.eleven.gateway.admin.domain.entity.GateResourcePack;
import com.eleven.gateway.admin.domain.entity.GateResourcePackContent;
import com.eleven.gateway.admin.domain.event.GateResourceUpdateEvent;
import com.eleven.gateway.admin.domain.repository.GateResourcePackRepository;
import com.eleven.gateway.admin.domain.repository.GateResourceRepository;
import com.eleven.gateway.admin.domain.service.GateResourceService;
import com.eleven.gateway.admin.support.GatewayFactory;
import com.eleven.gateway.core.GatewayResource;
import com.eleven.gateway.core.GatewayResourceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGateResourceService implements GateResourceService, GatewayResourceLoader {

    private final JpaLobHelper jpaLobHelper;
    private final GatewayFactory gatewayFactory;
    private final GateResourceRepository resRepository;
    private final GateResourcePackRepository packRepository;
    private final SerialGenerator serialGenerator;

    @Override
    public Page<GateResource> queryService(GateResourceQueryAction action) {
        var page = resRepository.findAll(action.toSpecification(), action.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public Optional<GateResource> findService(String id) {
        return resRepository.findById(id);
    }

    @Override
    public GateResource saveService(GateResource gateResource) {
        if (StringUtils.isNotBlank(gateResource.getId())) {
            GateResource exist = this.require(gateResource.getId());
            exist.update(gateResource);
            resRepository.save(exist);
            SpringUtil.publishEvent(new GateResourceUpdateEvent(exist.getId()));
            return exist;
        }

        gateResource.setId(serialGenerator.nextSerialWithPrefix(GateAdminConstants.RESOURCE_ID_PREFIX));
        resRepository.save(gateResource);
        SpringUtil.publishEvent(new GateResourceUpdateEvent(gateResource.getId()));
        return gateResource;
    }


    @Override
    public void deleteResource(String id) {
        this.findService(id).ifPresent(definition -> {
            resRepository.delete(definition);
            SpringUtil.publishEvent(new GateResourceUpdateEvent(id));
        });
    }


    @Override
    public Collection<GateResourcePack> listPacks(String resourceId) {
        return packRepository.findByResourceId(resourceId);
    }

    @Override
    public GateResourcePack uploadPack(GateResource gateResource, Resource resource) throws IOException {
        GateResourcePack pack = new GateResourcePack();
        pack.setFileName(resource.getFilename());
        pack.setResourceId(gateResource.getId());
        pack.setContent(new GateResourcePackContent().setBody(jpaLobHelper.createBlob(resource.getInputStream(), resource.getInputStream().available())));
        return packRepository.save(pack);
    }

    @Override
    public void deletePack(String id) {
        var pack = packRepository.getReferenceById(id);
        if (pack.isReleased()) {
            var resource = resRepository.getReferenceById(pack.getResourceId());
            resource.setPack(null);
            resRepository.save(resource);
            SpringUtil.publishEvent(new GateResourceUpdateEvent(resource.getId()));
        }
        packRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releasePack(String id) {
        var pack = packRepository.getReferenceById(id);
        var resource = resRepository.getReferenceById(pack.getResourceId());
        packRepository.updateLatest(resource.getId(), pack.getId());
        resource.release(pack);
        packRepository.save(pack);
        resRepository.save(resource);
        SpringUtil.publishEvent(new GateResourceUpdateEvent(resource.getId()));
    }


    private GateResource require(String id) {
        return findService(id).orElseThrow(() -> ProcessRejectException.of("服务不存在"));
    }


    @Override
    public Collection<GatewayResource> load() {
        return resRepository.findAll()
                .stream()
                .map(gatewayFactory::createResource)
                .collect(Collectors.toList());
    }
}
