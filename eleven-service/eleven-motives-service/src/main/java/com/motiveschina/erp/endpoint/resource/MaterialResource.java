package com.motiveschina.erp.endpoint.resource;

import java.util.Optional;
import com.eleven.framework.web.ResourceId;
import com.eleven.framework.web.annonation.AsRestApi;
import com.motiveschina.erp.application.convertor.MaterialConvertor;
import com.motiveschina.erp.application.model.MaterialDto;
import com.motiveschina.erp.domain.material.MaterialFinder;
import com.motiveschina.erp.domain.material.MaterialManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "material")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialResource {

    private final MaterialFinder materialFinder;
    private final MaterialConvertor materialConvertor;
    private final MaterialManager materialManager;

    @Operation(summary = "get a material")
    @GetMapping("/{id}")
    public Optional<MaterialDto> getMaterial(@PathVariable("id")ResourceId id) {
       // materialManager.createSampleMaterial();
        return  materialFinder.get(id.asLong())
            .map(materialConvertor::toDto);
    }


}
