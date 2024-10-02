package com.eleven.access.admin.support;

import com.cnetong.access.core.MessageLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MessageSearchResult {

    @Schema(description = "本页数据")
    private final MessageLog log;
    private final Map<String, List<String>> highlights;

    public MessageSearchResult(MessageLog log, Map<String, List<String>> highlights) {
        this.log = log;
        this.highlights = highlights;
    }
}
