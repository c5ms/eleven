package com.eleven.hotel.domain.core;

import com.eleven.core.domain.DomainEvent;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

@Getter
@EntityListeners(AbstractEntityListener.class)
@FieldNameConstants
@MappedSuperclass
@TableGenerator(table = AbstractEntity.GENERATOR_TABLE, name = AbstractEntity.GENERATOR_NAME, allocationSize = 5)
public abstract class AbstractEntity {

    public static final String GENERATOR_NAME = "hms_generator";
    public static final String GENERATOR_TABLE = "hms_sequences";

    @Getter(AccessLevel.PROTECTED)
    private transient final @Transient List<DomainEvent> domainEvents = new ArrayList<>();

}
