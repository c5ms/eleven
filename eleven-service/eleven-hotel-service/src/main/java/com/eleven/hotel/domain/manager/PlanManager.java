package com.eleven.hotel.domain.manager;

import com.eleven.core.domain.DomainError;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.plan.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;
    private final InventoryRepository inventoryRepository;

    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan).ifPresent(DomainError::throwException);
        }
    }


}
