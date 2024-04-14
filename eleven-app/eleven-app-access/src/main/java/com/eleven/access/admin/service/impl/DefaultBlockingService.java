package com.eleven.access.admin.service.impl;

import com.cnetong.access.admin.domain.action.BlockingQueryAction;
import com.cnetong.access.admin.domain.action.MessageBlockingSolveAction;
import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.access.admin.domain.repository.BlockingRepository;
import com.cnetong.access.admin.service.BlockingService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.query.domain.QueryHelper;
import com.cnetong.common.web.ProcessRejectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultBlockingService implements BlockingService {
    private final BlockingRepository blockingRepository;

    @Override
    public MessageBlocking require(String id) {
        return get(id).orElseThrow(() -> ProcessRejectException.of("阻塞不存在"));
    }

    @Override
    public Optional<MessageBlocking> get(String id) {
        return blockingRepository.findById(id);
    }


    @Override
    public Page<MessageBlocking> query(BlockingQueryAction queryAction) {
        var page = blockingRepository.findAll(queryAction.toSpecification(), queryAction.toPageable());
        return QueryHelper.toPageList(page);
    }

    @Override
    public void solve(MessageBlockingSolveAction action) {
        blockingRepository.findById(action.getBlockingId())
                .ifPresent(messageBlocking -> {
                    messageBlocking.setSolution(action.getSolution());
                    messageBlocking.setSolve(true);
                    blockingRepository.save(messageBlocking);
                });
    }

    @Override
    public long countByListenerId(String listenerId) {
        return blockingRepository.countByListenerId(listenerId);
    }


    @Override
    public MessageBlocking save(MessageBlocking messageBlocking) {
        return blockingRepository.save(messageBlocking);
    }


    @Override
    public void delete(MessageBlocking messageBlocking) {
        blockingRepository.delete(messageBlocking);
    }

    @Override
    public Collection<MessageBlocking> list() {
        return blockingRepository.findAll();
    }

}
