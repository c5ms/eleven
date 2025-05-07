package com.motiveschina.hotel.features.plan;

import java.util.Objects;
import java.util.Optional;
import com.eleven.framework.domain.Specifications;
import com.motiveschina.hotel.core.support.ContextSupport;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanFinder {

    private final PlanRepository planRepository;

    @Transactional(readOnly = true)
    public Optional<Plan> readPlan(PlanKey planKey) {
        return planRepository.findByKey(planKey).filter(ContextSupport::mustReadable);
    }


    @Transactional(readOnly = true)
    public Page<Plan> queryPage(PlanFilter filter, Pageable pageable) {
        var spec = Specifications.query(Plan.class)
            .and(StringUtils.isNotBlank(filter.getPlanName()), Specs.nameLike(filter.getPlanName()))
            .and(Objects.nonNull(filter.getHotelId()), Specs.hotelIdIs(filter.getHotelId()))
            .getSpec();
        return planRepository.findAll(spec, pageable);

    }

    @UtilityClass
    public class Specs {

        Specification<Plan> nameLike(@Nullable String name) {
            return (root, query, builder) ->
                builder.like(root.get(Plan.Fields.basic).get(PlanBasic.Fields.name), "%" + name + "%");
        }

        Specification<Plan> hotelIdIs(@Nullable Long hotelId) {
            return (root, query, builder) ->
                builder.equal(root.get(Plan.Fields.hotelId), hotelId);
        }
    }

}
