package com.eleven.domain.hotel;

import com.eleven.core.infrastructure.jpa.Specifications;
import com.eleven.domain.plan.values.PlanBasic;
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

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelFinder {

    private final HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public Optional<Hotel> read(Long hotelId) {
        return hotelRepository.findById(hotelId);
    }

    @Transactional(readOnly = true)
    public Page<Hotel> queryPage(HotelFilter filter, Pageable pageable) {
        var spec = Specifications.query(Hotel.class)
                .and(StringUtils.isNotBlank(filter.getHotelName()), Specs.nameLike(filter.getHotelName()))
                .getSpec();
        return hotelRepository.findAll(spec, pageable);
    }

    @UtilityClass
    public class Specs {

        Specification<Hotel> nameLike(@Nullable String name) {
            return (root, query, builder) ->
                    builder.like(root.get(Hotel.Fields.basic).get(PlanBasic.Fields.name), "%" + name + "%");
        }

    }

}
