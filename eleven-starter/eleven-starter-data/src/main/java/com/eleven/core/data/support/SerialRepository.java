package com.eleven.core.data.support;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SerialRepository extends CrudRepository<Serial, String> {

    Optional<Serial> findByKey(Serial.Key key);

    void deleteByKey(Serial.Key key);
}
