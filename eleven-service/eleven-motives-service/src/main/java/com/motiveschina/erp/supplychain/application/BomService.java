package com.motiveschina.erp.supplychain.application;

import com.motiveschina.erp.supplychain.domain.Bom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BomService {

    @PreAuthorize("hasRole(@roles.approver) || isAnonymous()")
    public Bom readBom(Long id) {
        return new Bom()
            .setId(id)
            ;
    }

}
