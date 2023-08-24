package com.eleven.endpoint;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Record {
    @Id
    private String id;

    private String title;

    private org.bson.Document object;
}

