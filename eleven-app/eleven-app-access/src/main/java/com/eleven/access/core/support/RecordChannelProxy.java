package com.eleven.access.core.support;

import com.cnetong.access.core.RecordChannel;
import com.cnetong.access.core.RecordConverter;

import java.util.ArrayList;
import java.util.List;

public class RecordChannelProxy implements RecordChannel {

    private final RecordChannel actual;
    public final List<RecordConverter> converters = new ArrayList<>();

    public RecordChannelProxy(RecordChannel actual) {
        this.actual = actual;
    }

    public void addConvertor(RecordConverter converter) {
        this.converters.add(converter);
    }

    @Override
    public void write(Record record) throws Exception {
        for (RecordConverter converter : this.converters) {
            record = converter.convert(record);
        }
        actual.write(record);
    }

    @Override
    public void close() throws Exception {
        actual.close();
    }
}
