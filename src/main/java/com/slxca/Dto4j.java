package com.slxca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.slxca.annotation.Dto;
import com.slxca.converter.DtoConverter;
import com.slxca.annotation.DtoProperty;
import com.slxca.converter.NoConverter;

import java.lang.reflect.Field;
import java.util.*;

public class Dto4j {

    private String PROFILE = null;
    private static final Map<Class<?>, DtoConverter<?, ?>> CONVERTER_CACHE = new HashMap<>();


    private final Map<String, Object> mapData = new HashMap<>();
    private final List<Object> listData = new ArrayList<>();


    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static Dto4j builder() {
        return new Dto4j();
    }

    public Dto4j profile(String profile) {
        this.PROFILE = profile;
        return this;
    }

    public Dto4j object(Object object) {
        if (object == null) {
            return this;
        }

        serializeObject(object);
        return this;
    }

    public Dto4j list(List<?> list) {
        if (list == null) {
            return this;
        }

        serializeList(list);
        return this;
    }

    public Dto4j add(String key, Object value) {
        mapData.put(key, value);
        return this;
    }

    public Dto4j addAll(Map<String, Object> map) {
        mapData.putAll(map);
        return this;
    }

    public Dto4j addAll(Dto4j dto) {
        mapData.putAll(dto.mapData);
        return this;
    }

    public Dto4j remove(String key) {
        mapData.remove(key);
        return this;
    }

    public String toJson() {
        try {
            Map<String, Object> sortedData = new LinkedHashMap<>();

            mapData.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> typePriority(e.getValue())))
                    .forEachOrdered(e -> sortedData.put(e.getKey(), e.getValue()));

            return objectMapper.writeValueAsString(sortedData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : mapData.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        return new HashMap<>(mapData);
    }

    public List<Object> toList() {
        return new ArrayList<>(listData);
    }

    private void serializeList(Iterable<?> iterable) {
        if(iterable == null) return;

        for (Object element : iterable) {
            Class<?> clazz = element.getClass();

            if (!clazz.isAnnotationPresent(Dto.class)) return;

            Map<String, Object> map = new HashMap<>();

            for (Field field : clazz.getDeclaredFields()) {
                Map<String, Object> fieldMap = serializeField(field, element);
                if (fieldMap == null) continue;
                map.putAll(fieldMap);
            }

            listData.add(map);
        }
    }

    private void serializeObject(Object object) {
        if (object == null) return;

        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(Dto.class)) return;

        Map<String, Object> map = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            Map<String, Object> fieldMap = serializeField(field, object);
            if (fieldMap == null) continue;
            map.putAll(fieldMap);
        }

        mapData.putAll(map);
    }

    private Map<String, Object> serializeField(Field field, Object object) {
        try {
            DtoProperty annotation = field.getAnnotation(DtoProperty.class);

            if(annotation == null) return null;
            if(annotation.ignore()) return null;

            field.setAccessible(true);

            String name = annotation.name().isEmpty() ? field.getName() : annotation.name();
            Object value = serializeValue(field.get(object), annotation);

            return Collections.singletonMap(name, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object serializeValue(Object value, DtoProperty dtoProperty) {
        List<String> profiles = new ArrayList<>();
        Collections.addAll(profiles, dtoProperty.profile());
        Collections.addAll(profiles, dtoProperty.value());

        if(PROFILE == null) {
            if(!profiles.isEmpty()) return null;
        } else {
            if(!profiles.contains(PROFILE)) return null;
        }

        if (dtoProperty.converter() != NoConverter.class) {
            value = applyConverter(dtoProperty.converter(), value);
        }

        if (isDtoObject(value)) {
            value = Dto4j.builder().profile(PROFILE).object(value).toMap();
        }

        if (value instanceof Iterable<?>) {
            List<Object> serializedList = new ArrayList<>();
            for (Object element : (Iterable<?>) value) {
                if (isDtoObject(element)) {
                    Map<?, ?> profileMap = Dto4j.builder().profile(PROFILE).object(element).toMap();
                    if(profileMap.isEmpty()) continue;
                    serializedList.add(profileMap);
                } else {
                    serializedList.add(element);
                }
            }
            value = serializedList;
        }

        return value;
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


    private int typePriority(Object value) {
        if (value instanceof String || value instanceof Boolean || value instanceof Map) return 0;
        if (value instanceof Number) return 1;
        if (value instanceof Collection || value instanceof Object[]) return 2;
        return 3;
    }
}
