package com.eleven.core.test.utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@UtilityClass
public class Domains {

    public void nonNullField(Object object, boolean includeSimpleProperty) {
        try {
            var properties = BeanUtils.getPropertyDescriptors(object.getClass());
            for (PropertyDescriptor property : properties) {
                var value = property.getReadMethod().invoke(object);

                if (BeanUtils.isSimpleProperty(property.getPropertyType()) && !includeSimpleProperty) {
                    continue;
                }

                assertNotNull(value, property.getName() + " should not read null");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
