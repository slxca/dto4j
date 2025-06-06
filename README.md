![GitHub Release](https://img.shields.io/github/v/release/slxca/dto4j?display_name=tag&style=for-the-badge&label=Latest%20Version) 
![GitHub License](https://img.shields.io/github/license/slxca/dto4j?style=for-the-badge&label=License)


# dto4j

**dto4j** is a lightweight Java library for flexible serialization of DTO (Data Transfer Object) classes. Using annotations and configurable profiles, you can selectively convert complex objects into Maps or JSON strings—ideal for APIs, logging, or configuration purposes.

---

## Table of Contents

* [Features](#features)
* [Installation](#installation)
* [Example](#example)

    * [Defining DTO Classes](#defining-dto-classes)
    * [Using dto4j](#using-dto4j)
* [API Overview](#api-overview)

    * [Dto4j Class](#dto4j-class)
    * [Annotations](#annotations)
    * [DtoConverter Interface](#dtoconverter-interface)
* [Custom Converter Example](#custom-converter-example)
* [Dependencies](#dependencies)
* [License](#license)

---

## Features

* Annotation-driven serialization with `@Dto` and `@DtoProperty`
* Profile-based selective field serialization
* Custom field names and ignore option
* Support for custom converters (`DtoConverter`)
* Recursive serialization of nested DTOs
* Serialization of collections containing DTO elements
* Output as JSON (via Jackson) or as Map/String

---

## Installation

### Maven

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/slxca/dto4j</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.slxca</groupId>
  <artifactId>dto4j</artifactId>
  <version>1.1.0</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/slxca/dto4j")
    }
}

implementation 'com.slxca:dto4j:1.1.0'
```

---

## Example

### Defining DTO Classes

```java
import com.slxca.annotation.Dto;
import com.slxca.annotation.DtoProperty;

@Dto
public class Person {

    @DtoProperty(name = "firstName")
    private String name;

    @DtoProperty(profile = {"detailed"})
    private int age;

    @DtoProperty(ignore = true)
    private String password;

    // Constructor, getters, setters
}
```

### Using dto4j

```java
Person person = new Person("Max", 30, "secret123");

String jsonDefault = Dto4j.builder()
    .object(person)
    .toJson();
// Output: {"firstName":"Max"}

String jsonDetailed = Dto4j.builder()
    .profile("detailed")
    .object(person)
    .toJson();
// Output: {"firstName":"Max","age":30}
```

---

## API Overview

### Dto4j Class

* `static Dto4j builder()` — Creates a new builder instance.
* `Dto4j profile(String profile)` — Sets the serialization profile (default: "\_default").
* `Dto4j object(Object object)` — Serializes an object annotated with `@Dto`.
* `Dto4j add(String key, Object value)` — Adds a key-value pair.
* `Dto4j addAll(Map<String,Object> map)` — Adds all entries from a Map.
* `Dto4j addAll(Dto4j dto)` — Adds all data from another Dto4j instance.
* `Dto4j remove(String key)` — Removes an entry by key.
* `Dto4j clear()` — Clears all stored data.
* `String toJson()` — Serializes the collected data as a JSON string.
* `String toString()` — Returns data as a string in `key=value;` format.
* `Map<String,Object> toMap()` — Returns a Map with the collected data.

### Annotations

* `@Dto` — Marks a class as a serializable DTO.
* `@DtoProperty` — Configures serialization of a field:

    * `name`: alternative field name
    * `ignore`: exclude the field from serialization
    * `profile`: array of profiles in which the field is included
    * `converter`: custom `DtoConverter` class for value transformation

### DtoConverter Interface

* `DtoConverter<S, T>` — Interface for custom converters transforming a value of type S to T.

---

## Custom Converter Example

```java
public class UpperCaseConverter implements DtoConverter<String, String> {
    @Override
    public String convert(String source) {
        return source == null ? null : source.toUpperCase();
    }
}
```

Usage in DTO:

```java
@DtoProperty(converter = UpperCaseConverter.class)
private String name;
```

---

## Dependencies

* Jackson Databind (`com.fasterxml.jackson.databind.ObjectMapper`)
* Jackson JavaTimeModule (`com.fasterxml.jackson.datatype.jsr310.JavaTimeModule`)

---

## License

This project is licensed under the MIT License.
