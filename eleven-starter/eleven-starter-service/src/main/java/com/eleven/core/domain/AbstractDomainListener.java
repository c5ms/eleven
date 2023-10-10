package com.eleven.core.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.AfterConvertEvent;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AbstractDomainListener extends AbstractRelationalEventListener<AbstractDomain> {

    @Override
    protected void onAfterConvert(AfterConvertEvent<AbstractDomain> event) {
        if (null != event.getEntity()) {
            event.getEntity().markOld();
        }
    }

    @Override
    protected void onBeforeSave(BeforeSaveEvent<AbstractDomain> event) {
        if (null != event.getEntity()) {
        }
    }

    @Override
    protected void onAfterSave(AfterSaveEvent<AbstractDomain> event) {
        if (null != event.getEntity()) {
            event.getEntity().markOld();
        }
    }
}
