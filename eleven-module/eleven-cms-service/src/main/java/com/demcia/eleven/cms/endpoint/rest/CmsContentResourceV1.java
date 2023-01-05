package com.demcia.eleven.cms.endpoint.rest;

import com.demcia.eleven.cms.core.action.CmsContentCreateAction;
import com.demcia.eleven.cms.core.action.CmsContentUpdateAction;
import com.demcia.eleven.cms.core.dto.CmsContentSummaryDto;
import com.demcia.eleven.cms.domain.convertor.CmsContentConverter;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import com.demcia.eleven.cms.domain.service.CmsContentService;
import com.demcia.eleven.core.rest.annonation.RestResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "内容")
@RequestMapping("/cms/contents")
@RestResource
@RequiredArgsConstructor
public class CmsContentResourceV1 {

    private final CmsContentService cmsContentService;
    private final CmsContentConverter cmsContentConverter;

    @Operation(summary = "内容创建")
    @PostMapping
    public CmsContentSummaryDto contentCreate(@RequestBody @Validated CmsContentCreateAction action) {
        var content = cmsContentService.createContent(action);
        return cmsContentConverter.toDto(content);
    }

    @Operation(summary = "内容删除")
    @DeleteMapping("/{id}")
    public void contentDelete(@PathVariable("id") String id) {
        CmsContent content = cmsContentService.requireContent(id);
        cmsContentService.deleteContent(content);
    }

    @Operation(summary = "内容更新")
    @PostMapping("/{id}")
    public CmsContentSummaryDto contentUpdate(@PathVariable("id") String id, @RequestBody @Validated CmsContentUpdateAction action) {
        CmsContent content = cmsContentService.requireContent(id);
        cmsContentService.updateContent(content, action);
        return cmsContentConverter.toDto(content);
    }

}
