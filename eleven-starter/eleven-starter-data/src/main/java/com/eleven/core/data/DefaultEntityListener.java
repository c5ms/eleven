package com.eleven.core.data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.AfterConvertEvent;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DefaultEntityListener extends AbstractRelationalEventListener<AbstractEntity> {

    @Override
    protected void onAfterConvert(AfterConvertEvent<AbstractEntity> event) {
        if (null != event.getEntity()) {
            event.getEntity().markOld();
        }
    }

    @Override
    protected void onBeforeSave(BeforeSaveEvent<AbstractEntity> event) {
        if (null != event.getEntity()) {

        }
    }

    @Override
    protected void onAfterSave(AfterSaveEvent<AbstractEntity> event) {
        if (null != event.getEntity()) {
            event.getEntity().markOld();
        }
    }
}
