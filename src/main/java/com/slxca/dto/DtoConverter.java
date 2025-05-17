package com.slxca.dto;

public interface DtoConverter<S, T> {
    T convert(S source);
}
