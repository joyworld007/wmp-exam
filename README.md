## EXAM

## Contents
* [Specifications](#chapter-1)
* [Requirement](#chapter-2)
* [How to run](#chapter-3)
* [How to Test](#chapter-4)


### <a name="chapter-1"></a>Specifications 
````
 OpenJDK11
 Spring Boot 2.3.0.RELEASE
 Spring Data Redis
 Embed Redis 
 Rest API
 Swagger2
 Thymeleaf 
````

### <a name="chapter-2"></a>Requirement 
````
URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service
예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

- URL 입력폼 제공 및 결과 출력
- URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
- 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
- 동일한 URL에 대한 요청 수 정보를 가져야 합니다.
- Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
- Unit test 및 Integration test 작성.
````

### <a name="chapter-3"></a>How to Run
```
1. build
./gradlew build

2. run
./gradlew bootrun

3. test 
./gradlew test
```

### <a name="chapter-4"></a>How to Test
1. Spring Boot Application을 실행 합니다. 

2.  http://localhost:8080/main 페이지에 접속 합니다.

