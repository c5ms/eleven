package com.eleven.booking.domain.core;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@EntityListeners(AbstractEntityListener.class)
@FieldNameConstants
@MappedSuperclass
@TableGenerator(table = AbstractEntity.GENERATOR_TABLE, name = AbstractEntity.GENERATOR_NAME, allocationSize = 5)
public abstract class AbstractEntity {

    public static final String GENERATOR_NAME = "bk_generator";
    public static final String GENERATOR_TABLE = "bk_sequences";


}
