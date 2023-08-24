package com.eleven.endpoint;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RecordRepository extends MongoRepository<Record, String> {

    @Query("{'object.user':'?0'}")
    List<Record> query(String user);
}
