#### 요구사항
* Java 17([Corretto])
* Spring Boot 3.1(호환성 이슈가 없다면 최신버전을 따릅니다.)
* 인메모리 H2DB
* Intellij IDE

#### 디렉토리 구성
```
.
├── application            // Service, Facade, Handler
         ├── command
├── domain                 // Entity
├── infrastructure
         ├── Persistence   // Repositoy
         ├── Web           // Controller
```

#### 모델 관계
```
Lecture(일) --> Ticket(다)

Lecture : 강연정보(강연자, 강연장소, 강연시각 ...)
Ticket : 수강신청(강연, 사번번호)
```

간단하게 보여 줄 내용으로만 작성했습니다.
#### 동시성보장 방법
```
  동시성을 보장하기 위해서 DB 비관적락 사용
```

#### 응답값 포멧
```
{
     "code": "0000",
     "message": "OK",
     "data": { data }
}
```
#### QueryDSL vs JPA
```
 조회 필터만 QueryDSL 사용하였고 그 이외에 나머지 기능들은 JPA 사용하였습니다.
```
#### api 문서
```
http://localhost:8080/swagger-ui/index.html
```

#### 코멘트
```
테스트코드 미 작성
```