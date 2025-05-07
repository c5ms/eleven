package com.motiveschina.erp.domain.material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import com.motiveschina.core.layer.DomainFinder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaterialFinder implements DomainFinder {

    private final MaterialRepository materialRepository;

    public Optional<Material> get(Long orderId) {
        return materialRepository.findById(orderId);
    }

    public List<Material> list(Collection<Long> ids) {
        return ListUtils.partition(new ArrayList<>(ids), 1000)
            .stream()
            .map(materialRepository::findAllById)
            .flatMap(Collection::stream)
            .toList();
    }

    @UtilityClass
    public class Specs {

    }

}
