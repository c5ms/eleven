package com.eleven.core.infrastructure.jpa;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public class Specifications<T> {

    private Specification<T> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

    public static <T> Specifications<T> query(Class<T> tClass) {
        return new Specifications<T>();
    }

    public Specifications<T> and(Boolean enable, Specification<T> spec) {
        if (enable) {
            this.spec = spec.and(spec);
        }
        return this;
    }

    public Specifications<T> or(Boolean enable, Specification<T> spec) {
        if (enable) {
            this.spec = spec.or(spec);
        }
        return this;
    }

    public Specifications<T> and(Specification<T> spec) {
        this.spec = spec.and(spec);
        return this;
    }

    public Specifications<T> or(Specification<T> spec) {
        this.spec = spec.or(spec);
        return this;
    }
}
