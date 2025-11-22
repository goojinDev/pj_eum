# 이음(Eum) - 청소년 정책 AI 상담 서비스 🌱

> 청소년들이 필요한 정책 정보를 AI 상담을 통해 쉽게 접근할 수 있도록 돕는 서비스

[![Java](https://img.shields.io/badge/Java-17-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-85EA2D.svg)](http://localhost:8080/swagger-ui.html)

---

## 📋 목차

- [프로젝트 소개](#-프로젝트-소개)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [주요 기능](#-주요-기능)
- [시작하기](#-시작하기)
- [API 문서](#-api-문서)
- [데이터베이스 스키마](#-데이터베이스-스키마)
- [개발 환경 설정](#-개발-환경-설정)
- [프로젝트 구조](#-프로젝트-구조)
- [컨벤션](#-컨벤션)
- [기여하기](#-기여하기)
- [라이센스](#-라이센스)

---

## 🎯 프로젝트 소개

**이음(Eum)**은 청소년들이 복잡한 정책 정보를 쉽게 이해하고 접근할 수 있도록 AI 기반 상담 서비스를 제공하는 플랫폼입니다.

### 주요 특징

- 🤖 **AI 기반 상담**: Azure OpenAI를 활용한 맞춤형 정책 상담
- 📊 **감정 분석**: 사용자의 감정 상태를 파악하여 적절한 응답 제공
- 🔍 **RAG 기반 검색**: 벡터 DB를 활용한 정확한 정책 정보 제공
- 👥 **관리자 모니터링**: 위험군 사용자 조기 발견 및 대응
- 📱 **멀티 플랫폼**: 웹, 모바일 앱 지원 (추후 구현)

### 개발 기간

- **2024.11 - 현재 진행 중**

---

## 🛠 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: PostgreSQL 16
- **ORM**: MyBatis 3.0.5
- **Authentication**: Spring Security + OAuth2
- **API Documentation**: Springdoc OpenAPI (Swagger) 2.3.0

### AI/ML
- **Model Server**: FastAPI
- **AI API**: Azure OpenAI
- **Workflow**: n8n

### Infrastructure
- **Cloud**: Azure
- **Build Tool**: Gradle

### Frontend (별도 레포지토리)
- **Framework**: React + Vite

---

## 🏗 시스템 아키텍처
```
┌─────────────┐
│   사용자    │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────────────┐
│            웹/앱 (React)                     │
└──────┬──────────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────────┐
│         n8n 워크플로우 엔진                  │
└──────┬──────────────────────────────────────┘
       │
       ├─────────┬──────────┬─────────────────┐
       ▼         ▼          ▼                 ▼
  ┌─────────┐ ┌────────┐ ┌──────────┐  ┌──────────┐
  │RAG(벡터)│ │AI Agent│ │감정 분석  │  │질문 생성 │
  │   DB    │ │        │ │  모델    │  │  모델    │
  └─────────┘ └────────┘ └──────────┘  └──────────┘
       │                       │
       ▼                       ▼
  ┌─────────────────────────────────┐
  │    Spring Boot Backend          │
  │    - REST API                   │
  │    - OAuth2 인증                │
  │    - 데이터 관리                │
  └──────────┬──────────────────────┘
             ▼
      ┌─────────────┐
      │ PostgreSQL  │
      │   Database  │
      └─────────────┘
```

---

## ✨ 주요 기능

### 1. 인증 및 권한 관리

#### 관리자 (Admin)
- ✅ 일반 로그인 (ID/PW)
- ✅ 회원가입 (그룹 소속)
- ✅ 비밀번호 변경
- ✅ 최초 로그인 감지

#### 사용자 (User)
- ✅ 일반 회원가입 (LOCAL)
- ✅ 소셜 로그인 (Google OAuth2)
- ✅ 로그인
- ✅ 정보 수정 (닉네임, 전화번호, 배경테마 등)
- ✅ 비밀번호 변경
- ✅ 회원 탈퇴

### 2. 데이터 관리

- 📊 **14개 테이블** 설계 완료
  - 조직/그룹 관리
  - 사용자/관리자 관리
  - 일반 채팅 / 이음(AI) 채팅
  - 감정 분석 결과
  - 사용자 일일 상태
  - 위험군 모니터링

### 3. API 문서화

- 📝 **Swagger UI** 통합
- 🧪 모든 API 테스트 가능
- 📖 자동 문서 생성

---

## 🚀 시작하기

### 필수 요구사항

- Java 17 이상
- PostgreSQL 16
- Gradle 8.x
- IntelliJ IDEA (권장)

### 설치 및 실행

1. **레포지토리 클론**
```bash
git clone https://github.com/yourusername/eum-backend.git
cd eum-backend
```

2. **데이터베이스 설정**
```bash
# PostgreSQL 접속
psql -U postgres

# 데이터베이스 생성
CREATE DATABASE eum_db;
```

3. **DDL 실행**
```bash
# src/main/resources/ddl/schema.sql 실행
psql -U postgres -d eum_db -f src/main/resources/ddl/schema.sql
```

4. **application.properties 설정**
```properties
# src/main/resources/application.properties

# PostgreSQL 설정
spring.datasource.url=jdbc:postgresql://localhost:5432/eum_db
spring.datasource.username=postgres
spring.datasource.password=your_password

# Google OAuth2 설정
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

5. **애플리케이션 실행**
```bash
./gradlew bootRun
```

6. **Swagger 접속**
```
http://localhost:8080/swagger-ui.html
```

---

## 📖 API 문서

### Base URL
```
http://localhost:8080
```

### 주요 엔드포인트

#### 관리자 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/admin/register` | 관리자 회원가입 |
| POST | `/api/admin/login` | 관리자 로그인 |
| POST | `/api/admin/change-password` | 비밀번호 변경 |

#### 사용자 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/user/register` | 회원가입 / 추가정보입력 |
| POST | `/api/user/login` | 로그인 |
| PATCH | `/api/user/update` | 정보 수정 |
| POST | `/api/user/change-password` | 비밀번호 변경 |
| POST | `/api/user/withdraw` | 회원 탈퇴 |

#### OAuth2

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/oauth2/authorization/google` | 구글 로그인 시작 |

### 상세 API 문서

Swagger UI에서 확인: http://localhost:8080/swagger-ui.html

---

## 🗄 데이터베이스 스키마

### 주요 테이블
```
pj_group              - 조직/그룹
pj_admin              - 관리자 계정
pj_admin_auth         - 관리자 권한
pj_user               - 사용자 계정
pj_user_option        - 사용자 설정
pj_user_chat_list     - 일반 채팅방
pj_user_chat_content  - 일반 채팅 내용
pj_eum_chat_list      - 이음(AI) 채팅방
pj_eum_chat_content   - 이음(AI) 채팅 내용
pj_user_emotion       - 감정 분석 결과
pj_user_life_data     - 사용자 일일 상태
pj_user_activity      - 사용자 활동 로그
pj_risk_monitoring    - 위험군 모니터링
pj_user_chat_macro    - 채팅 매크로
```

### ERD
```
pj_group
    ↓
pj_admin ──→ pj_admin_auth
    ↓
pj_user ──→ pj_user_option
    ↓       ↓
    ├───→ pj_user_chat_list ──→ pj_user_chat_content
    ├───→ pj_eum_chat_list ──→ pj_eum_chat_content
    ├───→ pj_user_emotion
    ├───→ pj_user_life_data
    ├───→ pj_user_activity
    └───→ pj_risk_monitoring
```

---

## 🔧 개발 환경 설정

### IntelliJ IDEA 설정

1. **Lombok 플러그인 설치**
   - Settings > Plugins > Lombok 검색 및 설치

2. **Annotation Processing 활성화**
   - Settings > Build, Execution, Deployment > Compiler > Annotation Processors
   - ✅ Enable annotation processing

3. **코드 스타일**
   - Settings > Editor > Code Style > Java
   - Indentation: 4 spaces

### Google OAuth2 설정

1. [Google Cloud Console](https://console.cloud.google.com/) 접속
2. 프로젝트 생성
3. OAuth 2.0 클라이언트 ID 생성
4. 승인된 리디렉션 URI 추가:
```
   http://localhost:8080/login/oauth2/code/google
```

---

## 📁 프로젝트 구조
```
src/
├── main/
│   ├── java/com/eum/pj_eum/
│   │   ├── config/              # 설정 클래스
│   │   │   ├── securityConfig.java
│   │   │   ├── swaggerConfig.java
│   │   │   ├── corsConfig.java
│   │   │   └── oAuth2SuccessHandler.java
│   │   ├── controller/          # REST API 컨트롤러
│   │   │   ├── adminController.java
│   │   │   └── userController.java
│   │   ├── service/             # 비즈니스 로직
│   │   │   ├── adminService.java
│   │   │   ├── userService.java
│   │   │   ├── customOAuth2UserService.java
│   │   │   └── impl/
│   │   │       ├── adminServiceImpl.java
│   │   │       └── userServiceImpl.java
│   │   ├── mapper/              # MyBatis 매퍼
│   │   │   ├── adminMapper.java
│   │   │   └── userMapper.java
│   │   ├── dto/                 # 데이터 전송 객체
│   │   │   ├── adminVO.java
│   │   │   ├── userVo.java
│   │   │   ├── loginRequest.java
│   │   │   ├── loginResponse.java
│   │   │   ├── userRegisterRequest.java
│   │   │   ├── adminRegisterRequest.java
│   │   │   ├── passwordChangeRequest.java
│   │   │   ├── userWithdrawRequest.java
│   │   │   └── oAuth2UserInfo.java
│   │   └── PjEumApplication.java
│   └── resources/
│       ├── mapper/              # MyBatis XML
│       │   ├── adminMapper.xml
│       │   └── userMapper.xml
│       ├── ddl/                 # 데이터베이스 스키마
│       │   └── schema.sql
│       ├── static/
│       ├── templates/
│       └── application.properties
└── test/
    └── java/com/eum/pj_eum/
```

---

## 📜 컨벤션

### 코드 스타일

- **파일명**: camelCase (소문자 시작)
  - ✅ `adminController.java`
  - ✅ `userService.java`

- **클래스명**: PascalCase
  - ✅ `adminVO`
  - ✅ `userRegisterRequest`

- **메서드명**: camelCase
  - ✅ `findByEmail()`
  - ✅ `updateUserInfo()`

### Git Commit 메시지
```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 포맷팅, 세미콜론 누락 등
refactor: 코드 리팩토링
test: 테스트 코드
chore: 빌드 업무 수정, 패키지 매니저 수정
```

### API Response 형식
```json
{
  "userId": "USER-xxxxx",
  "email": "user@example.com",
  "name": "홍길동",
  "token": "jwt_token_here"
}
```


---

**Made with ❤️ by 이음 개발팀**
