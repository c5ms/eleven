package com.eleven.hotel.application.service;

import com.eleven.hotel.api.interfaces.model.PlanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanHandler {
    private final MongoTemplate mongoTemplate;

    public void sync(PlanDto dto) {
        mongoTemplate.insert(dto,"plan");
    }

}
