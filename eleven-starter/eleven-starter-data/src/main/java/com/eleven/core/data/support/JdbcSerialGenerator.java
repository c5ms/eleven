package com.eleven.core.data.support;

import com.eleven.core.data.SerialGenerator;
import com.eleven.core.data.configure.ElevenDataProperties;
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


    public final ReentrantLock lock = new ReentrantLock();
    private final Map<Serial.Key, Serial> serials = new ConcurrentHashMap<>();

    private final JdbcSerialMaintainer jdbcSerialMaintainer;


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

        private final ElevenDataProperties elevenDataProperties;
        private final SerialRepository serialRepository;

        @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
        Serial loadSerial(Serial.Key key) {
            var serialOptional = serialRepository.findByKey(key);

            if (serialOptional.isEmpty()) {
                Serial serial = Serial.create(key, 0, elevenDataProperties.getSerialCacheSize());
                serialRepository.save(serial);
                return serial;
            }

            Serial serial = serialOptional.get();
            serial.setStep(elevenDataProperties.getSerialCacheSize());
                serial.refresh();
            serialRepository.save(serial);
            return serial;
        }

        void delete(Serial.Key key) {
            serialRepository.deleteByKey(key);
        }
    }


}
