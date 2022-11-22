package com.demcia.eleven.core.pageable;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Schema(description = "分页结果")
public class PageResult<T> implements Iterable<T> {

	@Schema(description = "每页条数")
	private int pageSize;

	@Schema(description = "总计条数")
	private long totalSize;

	@Schema(description = "本页数据")
	private Collection<T> elements;

	public PageResult(Collection<T> elements) {
		this.elements = Objects.isNull(elements)
			? Collections.emptyList()
			: Collections.unmodifiableCollection(elements);
		this.pageSize = this.elements.size();
	}

	public static <T> PageResult<T> of(Collection<T> elements) {
		return new PageResult<>(elements);
	}

	public static <T> PageResult<T> of(PageResult<T> pageResult) {
		return new PageResult<>(pageResult.elements)
			.withSize(pageResult.pageSize)
			.withTotalSize(pageResult.totalSize);
	}

	public static <T> PageResult<T> of(PageResult<T> pageResult, Collection<T> elements) {
		return new PageResult<>(elements)
			.withSize(pageResult.pageSize)
			.withTotalSize(pageResult.totalSize);
	}

	public static <T> PageResult<T> empty() {
		return new PageResult<>(Collections.emptyList());
	}

	public PageResult<T> withSize(int size) {
		this.pageSize = size;
		return this;
	}

	public PageResult<T> withTotalSize(long totalSize) {
		this.totalSize = totalSize;
		return this;
	}

	public <R> PageResult<R> withElements(Collection<R> elements) {
		return new PageResult<>(elements)
			.withTotalSize(this.totalSize)
			.withSize(this.pageSize);
	}

	public <R> PageResult<R> withElements(Iterable<R> elements) {
		var list = new ArrayList<R>();
		elements.forEach(list::add);
		return new PageResult<>(list)
			.withTotalSize(this.totalSize)
			.withSize(this.pageSize);
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPages() {
		var size = this.getPageSize();
		var totalSize = this.getTotalSize();
		return size == 0 ? 1 : (int) Math.ceil((double) totalSize / (double) size);
	}


	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public <R> PageResult<R> map(Function<T, R> mapper) {
		return new PageResult<>(this.elements.stream().map(mapper).collect(Collectors.toUnmodifiableList()))
			.withTotalSize(this.totalSize);
	}


	public <S> PageResult<S> mapDto(Class<S> tClass) {
		return this.map(t -> {
			try {
				S instance = tClass.getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(t, instance);
				return instance;
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
					 NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public Collection<T> getElements() {
		return elements;
	}

	public void setElements(Collection<T> elements) {
		this.elements = elements;
	}

	@Override
	public Iterator<T> iterator() {
		return this.elements.iterator();
	}

	public Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	public List<T> list() {
		return new ArrayList<>(this.getElements());
	}
}