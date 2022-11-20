package com.eleven.access.admin.endpoint;

import com.cnetong.access.core.ComponentManager;
import com.cnetong.access.core.ComponentSpecification;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@Tag(name = "component", description = "组件库")
@RestApi
@RestController
@RequiredArgsConstructor
@RequestMapping("/components")
public class ComponentResourceV1 {
    private final ComponentManager componentManager;

    @Operation(summary = "列出所有在线组件")
    @GetMapping
    public Set<ComponentSpecification> listComponents() {
        return componentManager.components();
    }
}
