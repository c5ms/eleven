package com.demcia.eleven.core.rest.export;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
public class ExportData<T> {

	String title;

	Class<T> type;

	Collection<T> elements;

	Collection<String> headers;

}
