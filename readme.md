[![](https://jitpack.io/v/peavers/json-micro-library.svg)](https://jitpack.io/#peavers/json-micro-library)
[![Maintainability](https://api.codeclimate.com/v1/badges/9837fc6afdb9736a3f1a/maintainability)](https://codeclimate.com/github/peavers/json-micro-library/maintainability)

# Json micro library
A very tiny wrapper around `jsonapi-converter` for reading/writing JSON API payloads.

## What's a micro library? 
Just that, a very tiny standalone .jar which can be imported into a project to do a very specific thing. 

## Installation
Since we're making good use of JitPack, this is simple. 

```groovy

repositories {
    maven { url 'https://jitpack.io' }
}

	
dependencies {
    implementation 'com.github.peavers:json-micro-library:-SNAPSHOT'
}	
```
Click the JitPack build badge for more examples with maven, sbt example. 

## Spring boot example

Wire up the `JsonService`
```java
@Configuration
public class JsonServiceConfig {

    @Bean
    public JsonService jsonService() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);

        ResourceConverter resourceConverter = new ResourceConverter(objectMapper,
                HelloWorld.class);

        return new JsonServiceImpl(resourceConverter);
    }
}
```