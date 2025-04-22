package com.motiveschina.erp.supplychain.endpoint.resource;

import com.eleven.framework.web.ResourceId;
import com.eleven.framework.web.annonation.AsRestApi;
import com.motiveschina.erp.supplychain.application.BomService;
import com.motiveschina.erp.supplychain.domain.Bom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AsRestApi
@RequestMapping("/bom")
@RequiredArgsConstructor
public class BomResource {

    private final BomService bomService;

    @RequestMapping("/{id}")
    public Bom readBom(@PathVariable("id") ResourceId id) {
        return bomService.readBom(id.val());
    }
}
