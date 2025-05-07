package com.motiveschina.erp.domain.purchase;

import com.motiveschina.core.layer.DomainValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrderMaterial implements DomainValue {

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "material_code", unique = true, nullable = false)
    private String materialCode;

    public static PurchaseOrderMaterial of(Long materialId, String materialCode) {
        return new PurchaseOrderMaterial(materialId, materialCode);
    }

}
