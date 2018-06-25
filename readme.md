[![](https://jitpack.io/v/peavers/jsn-micro-library.svg)](https://jitpack.io/#peavers/json-micro-library)

# Json Micro Library
A very tiny wrapper around `jsonapi-converter` for reading/writing JSON API payloads.

## Installation
Since we're making good use of JitPack, this is simple. 

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
	
dependencies {
    implementation 
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
                Steak.class, NativeBuild.class);

        return new JsonServiceImpl(resourceConverter);
    }
}
```