package com.motiveschina.erp.domain.material;

import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "material_detail")
@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaterialDetail implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "material_composition")
    private String materialComposition;

    @Column(name = "color_fastness_grade")
    private String colorFastnessGrade;

    @Column(name = "environmental_certification")
    private String environmentalCertification;

    @Column(name = "fabric_weight")
    private double fabricWeight;

    @Column(name = "shrinkage_rate")
    private double shrinkageRate;

    @Column(name = "tensile_strength")
    private double tensileStrength;

    // you can choose to use constructor method or a static factory method
    @Builder
    @SuppressWarnings("unused")
    private MaterialDetail(String materialComposition,
                           String colorFastnessGrade,
                           String environmentalCertification,
                           double fabricWeight,
                           double shrinkageRate,
                           double tensileStrength) {
        this.materialComposition = materialComposition;
        this.colorFastnessGrade = colorFastnessGrade;
        this.environmentalCertification = environmentalCertification;
        this.fabricWeight = fabricWeight;
        this.shrinkageRate = shrinkageRate;
        this.tensileStrength = tensileStrength;
    }
}
