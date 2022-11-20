package com.eleven.core.data.support;

import com.eleven.core.data.SerialGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class JdbcSerialGenerator implements SerialGenerator {

    public ReentrantLock lock = new ReentrantLock();

    private final JdbcSerialMaintainer jdbcSerialMaintainer;

    private final Map<Serial.Key, Serial> serials = new ConcurrentHashMap<>();

    @Override
    public long next(String group, String name) {
        var key = Serial.keyOf(group, name);
        var serial = getSerial(key);
        return serial.nextVal();
    }

    private Serial getSerial(Serial.Key key) {
        lock.lock();
        try {
            var serial = serials.computeIfAbsent(key, jdbcSerialMaintainer::loadSerial);
            if (serial.isExhausted()) {
                serials.remove(key);
                serial = serials.computeIfAbsent(key, jdbcSerialMaintainer::loadSerial);
            }
            return serial;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long drop(String group, String name) {
        var key = Serial.keyOf(group, name);
        serials.remove(key);
        jdbcSerialMaintainer.delete(key);
        return 0;
    }

    @Component
    @RequiredArgsConstructor
    public static class JdbcSerialMaintainer {

        private final SerialRepository serialRepository;

        @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
        Serial loadSerial(Serial.Key key) {
            var serial = serialRepository.findByKey(key).orElseGet(() -> createSerial(key));
            serial.refresh();
            serialRepository.save(serial);
            return serial;
        }

        private Serial createSerial(Serial.Key key) {
            Serial serial = Serial.create(key, 10, 5);
            serialRepository.save(serial);
            return serial;
        }


        public void delete(Serial.Key key) {
            serialRepository.deleteByKey(key);
        }
    }


}
