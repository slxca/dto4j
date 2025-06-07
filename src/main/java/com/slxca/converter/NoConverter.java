package com.slxca.converter;

public class NoConverter implements DtoConverter<Object, Object> {
    @Override
    public Object convert(Object source) {
        return source;
    }
}
