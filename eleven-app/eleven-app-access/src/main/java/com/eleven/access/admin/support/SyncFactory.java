package com.eleven.access.admin.support;

import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.access.admin.domain.entity.SyncTaskMapping;
import com.cnetong.access.admin.domain.entity.SyncTaskWriter;
import com.cnetong.access.core.*;
import com.cnetong.access.core.support.DefaultRecordConverter;
import com.cnetong.common.persist.generator.CompositeGenerator;
import com.cnetong.common.schedule.EpeiusTask;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncFactory {
    private final ComponentManager componentManager;
    private final ApplicationContext applicationContext;

    public RecordConverter createConvertor(SyncTask syncTask) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
        evaluationContext.setVariable("generator", applicationContext.getBean(CompositeGenerator.class));

        DefaultRecordConverter converter = new DefaultRecordConverter(evaluationContext);
        for (SyncTaskMapping mapping : syncTask.getMappings()) {
            converter.addMapping(
                    mapping.getCollection(),
                    mapping.getSourceName(),
                    mapping.getTargetName(),
                    mapping.getTransformer()
            );
        }
        return converter;
    }

    public RecordWriter createWriter(SyncTaskWriter writer) {
        return componentManager.createComponent(writer.getWriterType(), writer.getWriterConfig(), RecordWriterFactory.class);
    }

    public RecordReader createReader(SyncTask syncTask) {
        return componentManager.createComponent(syncTask.getReaderType(), syncTask.getReaderConfig(), RecordReaderFactory.class);
    }

    public EpeiusTask createTask(SyncTask syncTask) {
        return EpeiusTask.builder()
                .id(syncTask.getId())
                .job("record_sync")
                .build();
    }


}
