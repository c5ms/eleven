package com.motiveschina.erp.domain.material.event;

import com.motiveschina.core.layer.DomainEvent;
import com.motiveschina.erp.domain.material.Material;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class MaterialDeletedEvent implements DomainEvent {

    Material material;

}
