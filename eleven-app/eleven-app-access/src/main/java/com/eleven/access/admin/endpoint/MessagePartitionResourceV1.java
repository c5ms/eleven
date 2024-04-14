package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.entity.MessagePartitionDefinition;
import com.cnetong.access.admin.service.PartitionService;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestApi
@Tag(name = "消息分区")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/partitions")
public class MessagePartitionResourceV1 {

    private final PartitionService partitionService;

    @GetMapping
    @Operation(summary = "列出分区")
    public Collection<MessagePartitionDefinition> listPartitions() {
        return partitionService.listPartitions();
    }


    @DeleteMapping("/{partition}")
    @Operation(summary = "删除分区")
    public void deletePartitions(@PathVariable("partition") String partition) {
        partitionService.dropPartition(partition);
    }

    @Operation(summary = "保存分区")
    @PostMapping()
    public MessagePartitionDefinition saveEndpoint(@RequestBody @Validated MessagePartitionDefinition messagePartitionDefinition) {
        return partitionService.save(messagePartitionDefinition);
    }

}
