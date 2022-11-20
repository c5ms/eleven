package com.eleven.core.security.support;

import cn.hutool.json.JSONUtil;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RedisSubjectStore implements SubjectStore {

    public final static String KEY_PREFIX = "subject";

    private final RedisTemplate<String, String> redisTemplate;

    private String toKey(Principal principal) {
        return String.format("%s:%s", KEY_PREFIX, principal.identity());
    }

    @Override
    public void save(Principal principal, Subject subject) {
        var key = toKey(principal);
        var value = JSONUtil.toJsonStr(subject);
        redisTemplate.opsForValue().set(key, value, Duration.ofHours(5));
    }

    @Override
    public void remove(Principal principal) {
        var key = toKey(principal);
        redisTemplate.delete(key);
    }

    @Override
    public Optional<Subject> retrieval(Principal principal) {
        var key = toKey(principal);
        var valueStr = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(valueStr)
            .filter(StringUtils::isNotBlank)
            .map(s -> JSONUtil.toBean(s, Subject.class));
    }
}
