package com.eleven.core.data.support;

import com.eleven.core.data.DomainRepository;

import java.util.Optional;

public interface SerialRepository extends DomainRepository<Serial, String> {

    Optional<Serial> findByKey(Serial.Key key);

    void deleteByKey(Serial.Key key);
}
