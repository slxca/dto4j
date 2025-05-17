package com.slxca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.slxca.dto.Dto;
import com.slxca.dto.DtoConverter;
import com.slxca.dto.DtoProperty;

import java.lang.reflect.Field;
import java.util.*;

public class Dto4j {

    private static final String DEFAULT_PROFILE = "_default";
    private String profile = DEFAULT_PROFILE;

    private static final Map<Class<?>, DtoConverter<?, ?>> CONVERTER_CACHE = new HashMap<>();

    private final Map<String, Object> data = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static Dto4j builder() {
        return new Dto4j();
    }

    public Dto4j profile(String profile) {
        this.profile = profile;
        return this;
    }

    public Dto4j object(Object object) {
        if (object == null) {
            return this;
        }

        serializeObject(object);
        return this;
    }

    public Dto4j add(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Dto4j addAll(Map<String, Object> map) {
        data.putAll(map);
        return this;
    }

    public Dto4j addAll(Dto4j dto) {
        data.putAll(dto.data);
        return this;
    }

    public Dto4j remove(String key) {
        data.remove(key);
        return this;
    }

    public Dto4j clear() {
        data.clear();
        return this;
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        return new HashMap<>(data);
    }

    public void serializeObject(Object object) {
        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(Dto.class)) return;

        for (Field field : clazz.getDeclaredFields()) {
            DtoProperty annotation = field.getAnnotation(DtoProperty.class);

            if(annotation == null) continue;
            if(annotation.ignore()) continue;
            if(!Arrays.asList(annotation.profile()).contains(profile)) continue;

            field.setAccessible(true);

            try {
                Object value = field.get(object);
                String name = annotation.name().isEmpty() ? field.getName() : annotation.name();

                if (annotation.converter() != NoConverter.class) {
                    value = applyConverter(annotation.converter(), value);
                }

                else if (isDtoObject(value)) {
                    value = Dto4j.builder().profile(profile).object(value).toMap();
                }

                else if (value instanceof Iterable<?>) {
                    List<Object> serializedList = new ArrayList<>();
                    for (Object element : (Iterable<?>) value) {
                        if (isDtoObject(element)) {
                            serializedList.add(Dto4j.builder().profile(profile).object(element).toMap());
                        } else {
                            serializedList.add(element);
                        }
                    }
                    value = serializedList;
                }

                data.put(name, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Object applyConverter(Class<? extends DtoConverter<?, ?>> converterClass, Object value) {
        try {
            DtoConverter<Object, Object> converter = (DtoConverter<Object, Object>) CONVERTER_CACHE.computeIfAbsent(converterClass, cls -> {
                try {
                    return (DtoConverter<?, ?>) cls.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Cannot instantiate converter: " + cls.getName(), e);
                }
            });

            return converter.convert(value);
        } catch (ClassCastException e) {
            throw new RuntimeException("Converter class " + converterClass.getName() + " does not match expected generic types for value: " + value, e);
        }
    }

    private boolean isDtoObject(Object obj) {
        if (obj == null) return false;
        Class<?> clazz = obj.getClass();

        if (clazz.isAnnotationPresent(Dto.class)) return true;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(DtoProperty.class)) return true;
        }

        return false;
    }
}
