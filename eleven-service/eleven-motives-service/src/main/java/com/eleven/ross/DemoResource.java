package com.eleven.ross;

import java.util.List;
import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AsRestApi
@RequestMapping
@RequiredArgsConstructor
public class DemoResource {

    @GetMapping
    public PageResponse<String> rooms() {
        return PageResponse.of(List.of("user","page"),10);
    }

}
