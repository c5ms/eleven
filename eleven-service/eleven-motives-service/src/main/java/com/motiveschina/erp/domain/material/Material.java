package com.motiveschina.erp.domain.material;

import java.time.LocalDate;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "material")
@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Material implements DomainEntity {

    @Id
    @Column(name = "material_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(name = "material_code", unique = true, nullable = false)
    private String materialCode;

    @Column(name = "material_name", nullable = false)
    private String materialName;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_category", nullable = false)
    private MaterialCategory materialCategory;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "safety_stock")
    private double safetyStock;

    @Column(name = "main_supplier_id")
    private String mainSupplierId;

    @Column(name = "create_date")
    private LocalDate createDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "detail_id")
    private MaterialDetail detail;

    @Builder
    @SuppressWarnings("unused")
    private Material(String materialCode,
                     String materialName,
                     MaterialCategory category,
                     String unit,
                     double safetyStock,
                     String mainSupplierId,
                     MaterialDetail detail) {
        this.setMaterialCode(materialCode);
        this.setMaterialName(materialName);
        this.setMaterialCategory(category);
        this.setUnit(unit);
        this.setSafetyStock(safetyStock);
        this.setMainSupplierId(mainSupplierId);
        this.setCreateDate(LocalDate.now());
        this.setDetail(detail);
    }


}
