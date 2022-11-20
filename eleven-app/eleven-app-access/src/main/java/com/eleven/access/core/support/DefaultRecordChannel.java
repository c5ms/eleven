package com.eleven.access.core.support;

import cn.hutool.core.io.IoUtil;
import com.cnetong.access.core.RecordChannel;
import com.cnetong.access.core.RecordConverter;
import com.cnetong.access.core.RecordWriter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@RequiredArgsConstructor
public class DefaultRecordChannel implements RecordChannel {
    private final List<RecordWriter> writers = new ArrayList<>();
    private final List<RecordConverter> converters = new ArrayList<>();
    private final LongAdder processCountAdder = new LongAdder();

    @Override
    public void write(Record record) throws Exception {
        if (Thread.interrupted()) {
            throw new TaskStoppedException("任务被强制终止");
        }

        for (RecordConverter converter : converters) {
            record = converter.convert(record);
        }

        for (RecordWriter writer : writers) {
            writer.write(record);
        }
        processCountAdder.increment();
    }

    public void addWriter(RecordWriter writer) {
        this.writers.add(writer);
    }

    public void addConverter(RecordConverter converter) {
        this.converters.add(converter);
    }

    public long getProcessCount() {
        return processCountAdder.sum();
    }

    @Override
    public void close() throws Exception {
        for (RecordWriter writer : this.writers) {
            IoUtil.closeIfPosible(writer);
        }
    }
}
