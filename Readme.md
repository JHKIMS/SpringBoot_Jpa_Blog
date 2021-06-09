## BOOT+JPA 블로그

### 기능 구현 정리
- https://velog.io/@ddwj

### 의존성 추가</br>
- Spring Boot DevTools</br>
- Lombok</br>
- Spring Data Jpa</br>
- MySql Driver</br>
- Spring Security</br>

```
       <!--추가 라이브러리-->
        <!-- 시큐리티 태그 라이브러리 -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
        </dependency>

        <!-- JSP 템플릿 엔진 -->
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
        </dependency>

        <!-- JSTL -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
        <!-- 추가 라이브러리 끝 -->
```