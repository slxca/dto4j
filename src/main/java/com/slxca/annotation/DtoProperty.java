package com.slxca.annotation;

import com.slxca.converter.DtoConverter;
import com.slxca.converter.NoConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoProperty {
    String[] value() default {};
    String[] profile() default {};
    String name() default "";
    boolean ignore() default false;
    Class<? extends DtoConverter<?, ?>> converter() default NoConverter.class;
}

