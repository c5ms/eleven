package com.eleven.endpoint;

import com.eleven.core.rest.annonation.RestResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Tag(name = "tryout")
@RequestMapping("/tryout")
@RestResource
@RequiredArgsConstructor
public class TryoutEndpoint {
    private final RecordRepository recordRepository;


    @GetMapping
    public List<Record> tyr(String name) {
        Record record = new Record();
        record.setTitle("年度报销记录");

        org.bson.Document object = new Document();
        object.put("user", name);
        object.put("year", "2022 年");

        record.setObject(object);

        recordRepository.save(record);
        return recordRepository.query(name);

    }

}
