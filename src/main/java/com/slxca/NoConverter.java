package com.slxca;

import com.slxca.dto.DtoConverter;

public class NoConverter implements DtoConverter<Object, Object> {
    @Override
    public Object convert(Object source) {
        return source;
    }
}
