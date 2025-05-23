package com.slxca.dto;

import com.slxca.NoConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoProperty {
    String name() default "";
    boolean ignore() default false;
    String[] profile() default {"_default"};
    Class<? extends DtoConverter<?, ?>> converter() default NoConverter.class;
}

