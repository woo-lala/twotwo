# 투투의 민족

## 프로젝트 개요
이 프로젝트는 음식점과 메뉴를 등록하고, 사용자가 편리하게 음식을 주문하고 리뷰를 작성할 수 있게 제공하는 음식 주문 관리 플랫폼입니다.
사용자는 원하는 음식점을 찾아 리뷰를 참고하여 주문할 수 있으며, 음식점 사장님은 직접 메뉴를 등록하고 관리할 수 있습니다.

<br>

## 주요 기능
✅ **사용자 권한에 따라 기능 접근 제한**

✅ **지역별 음식점 및 메뉴 등록**

✅ **AI 기반 메뉴 설명 추천**

✅ **온라인 및 대면 음식 주문**

✅ **사용자 리뷰 및 평점 기능**

✅ **음식점, 메뉴, 리뷰 검색 기능**

<br>

## 팀원 역할

- Spring Security 및 JWT 기반 인증/인가 적용
- MASTER, MANAGER, OWNER, CUSTOMER에 따라

<br>

## 기술 스택

### 📌 Backend
- **언어 및 프레임워크**
  - **Java 17**
  - **Spring Boot 3.4.2**
- **인증/인가**
  - **Spring Security (JWT)**
- **데이터베이스**
  - **Spring Data JPA**
  - **QueryDSL 5.0.0**
- **DTO 매핑**
  - **MapStruct 1.5.5**
- **테스트**
  - **JUnit**
  - **Mockito**
- **API 문서**
  - **Swagger 2.7.0**

### 📌 Database
- **PostgreSQL**

### 📌 Infra
- **배포 환경**
  - **AWS EC2**
  - **AWS RDS**
- **CI/CD**
  - **GitHub Actions**

### 📌 소통 방식
- **커뮤니케이션 툴**
  - **ZEP**: 프로젝트 관련 실시간 회의
  - **Slack**: 프로젝트 관련 정보 공유
- **회의 주기**
  - **스크럼**: 매일 오전 11시

<br>

## 소프트웨어 아키텍처

![Alt text](/docs/images/infra.png)

<br>

## ERD

![Alt text](/docs/images/erd.png)

<br>

## API docs

https://teamsparta.notion.site/API-1982dc3ef51480efa1c6ede750802705

<br>

## 실행 방법

1. ./gradlew build
2. java -jar [JAR 파일명].jar
3. http://localhost:8080/swagger-ui/index.html
   💡 해당 링크를 통해 API 문서를 확인할 수 있습니다.
