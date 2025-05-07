package com.motiveschina.erp.domain.material;

import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.util.RandomUtil;
import com.motiveschina.core.layer.DomainManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaterialManager implements DomainManager {

    private final MaterialRepository materialRepository;

    public static List<Material> generateTestMaterials(MaterialRepository materialRepository) {
        List<Material> materials = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            var detail = MaterialDetail.builder()
                .materialComposition("Cotton 80%, Polyester 20%")
                .colorFastnessGrade("Grade 4")
                .environmentalCertification("ISO 14001")
                .fabricWeight(200)
                .shrinkageRate(0.05)
                .tensileStrength(150)
                .build();

            var material = Material.builder()
                .materialCode("T00" + i)
                .materialName("Cotton - Polyester Blend " + i)
                .category(MaterialCategory.values()[RandomUtil.randomInt(MaterialCategory.values().length - 1)])
                .unit("Meter")
                .safetyStock(100)
                .mainSupplierId("S00" + i)
                .detail(detail)
                .build();

            Material savedMaterial = materialRepository.save(material);
            materials.add(savedMaterial);
        }
        return materials;
    }

    public void createSampleMaterial() {
        List<Material> materials = generateTestMaterials(materialRepository);
        materialRepository.saveAll(materials);
    }
}
