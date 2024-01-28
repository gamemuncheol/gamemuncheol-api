# 사용할 Java JDK 버전을 선택
FROM openjdk:17-oracle

COPY ./build/libs/*-SNAPSHOT.jar app.jar

ARG ENVIRONMENT

ENV SPRIING_PROFIlES_ACTIVE=${ENVIRONMENT}

ENV TZ Asia/Seoul

# 컨테이너가 시작될 때 실행할 명령어
ENTRYPOINT ["java","-jar","/app.jar"]
