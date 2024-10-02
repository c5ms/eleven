package com.eleven.access.admin.service;

import com.cnetong.access.admin.domain.action.BlockingQueryAction;
import com.cnetong.access.admin.domain.action.MessageBlockingSolveAction;
import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.common.query.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface BlockingService {

    MessageBlocking require(String id);

    Optional<MessageBlocking> get(String id);

    MessageBlocking save(MessageBlocking MessageBlocking);

    void delete(MessageBlocking MessageBlocking);

    Collection<MessageBlocking> list();

    Page<MessageBlocking> query(BlockingQueryAction queryAction);

    void solve(MessageBlockingSolveAction action);

    long countByListenerId(String endpointId);
}
