package com.eleven.access.core;

import java.util.Optional;

public interface MessageRepository {

    void dropPartition(String partition);

    void createPartition(String partition);

    void save(MessageLog log);

    void update(MessageLog log);

    Optional<MessageLog> get(String partition, String id);

    void delete(String partition, String id);
}
