package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.hotel.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RegisterCommandService {



}
