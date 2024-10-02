package com.eleven.gateway.admin.domain.service;

import com.eleven.gateway.admin.domain.action.GateStackQueryAction;
import com.eleven.gateway.admin.domain.entity.GateStack;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public interface GateStackService {

    Collection<GateStack> queryStack(GateStackQueryAction action);

    Optional<GateStack> findStack(String id);

    GateStack saveStack(GateStack gateStack);

    void deleteStack(String id);

    GateStack require(String id);

    void publish(GateStack api);

    void cancel(GateStack GateStack);
}
