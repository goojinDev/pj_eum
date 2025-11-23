# 이음(Eum) - 청소년 정책 AI 상담 서비스 🌱

> 청소년들이 필요한 정책 정보를 AI 상담을 통해 쉽게 접근할 수 있도록 돕는 서비스

[![Java](https://img.shields.io/badge/Java-17-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-85EA2D.svg)](http://localhost:8080/swagger-ui/index.html)

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
- 💬 **일반 채팅 기능**: ChatGPT와 같은 자유로운 대화형 AI 상담
- 📊 **감정 분석**: 사용자의 감정 상태를 파악하여 적절한 응답 제공
- 🌤️ **실시간 날씨 연동**: OpenWeatherMap API를 통한 날씨 정보 제공
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

### External APIs
- **Weather**: OpenWeatherMap API

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
  │RAG(벡터)│ │AI Agent│ │감정 분석  │  │질문 생성   │
  │   DB    │ │        │ │  모델    │  │  모델     │
  └─────────┘ └────────┘ └──────────┘  └──────────┘
       │                       │
       ▼                       ▼
  ┌─────────────────────────────────┐
  │    Spring Boot Backend          │
  │    - REST API                   │
  │    - OAuth2 인증                │
  │    - 날씨 API 연동              │
  │    - 채팅 시스템                │
  │    - 감정 점수 저장             │
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

### 2. 채팅 시스템 🆕

#### 이음 채팅 (감정 AI 측정)
- ✅ **하루 단위 자동 채팅방 생성**: 날짜별로 자동 관리
- ✅ **AI 주도 질문**: AI가 먼저 질문하고 사용자가 답변
- ✅ **감정 점수 저장**: AI 개발자가 분석한 감정 데이터 저장
- ✅ **채팅 목록 조회**: 첫 AI 질문 미리보기 (10글자 + ...)
- ✅ **메시지 저장**: AI 질문 및 사용자 답변 저장

#### 일반 채팅 (ChatGPT 기능)
- ✅ **자유로운 채팅방 생성**: 사용자가 원할 때 새 채팅방 생성
- ✅ **대화형 AI 상담**: 사용자 질문에 AI가 답변
- ✅ **채팅 목록 조회**: 마지막 메시지 미리보기 (10글자 + ...)
- ✅ **채팅방 삭제**: Soft delete 방식

### 3. 홈 화면 기능 🆕

- ✅ **시간대별 인사말**: 아침/오후/저녁 자동 감지
- ✅ **실시간 날씨 정보**: OpenWeatherMap API 연동
  - 온도, 날씨 상태, 습도, 풍속 제공
  - 한글 번역 지원 (맑음, 흐림, 비 등)
  - API 실패 시 기본값 제공
- ✅ **오늘의 기분 저장**: 좋아요/보통/안좋아요/피곤해요 선택 및 저장

### 4. 데이터 관리

- 📊 **14개 테이블** 설계 완료
  - 조직/그룹 관리
  - 사용자/관리자 관리
  - 일반 채팅 / 이음(AI) 채팅
  - 감정 분석 결과
  - 사용자 일일 상태
  - 위험군 모니터링

### 5. API 문서화

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

# OpenWeatherMap API 설정
openweather.api.key=YOUR_API_KEY

# MyBatis 설정
mybatis.mapper-locations=classpath:mapper/**/*.xml
mybatis.type-aliases-package=com.eum.pj_eum.dto
mybatis.configuration.map-underscore-to-camel-case=true

# Swagger 설정
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
springdoc.swagger-ui.tags-sorter=alpha
springdoc.swagger-ui.operations-sorter=alpha
springdoc.packages-to-scan=com.eum.pj_eum.controller
```

5. **OpenWeatherMap API 키 발급**
   - https://openweathermap.org 접속
   - 무료 회원가입
   - API Keys 메뉴에서 키 발급
   - application.properties에 추가

6. **애플리케이션 실행**
```bash
./gradlew bootRun
```

7. **Swagger 접속**
```
http://localhost:8080/swagger-ui/index.html
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
| POST | `/admin/register` | 관리자 회원가입 |
| POST | `/admin/login` | 관리자 로그인 |
| POST | `/admin/change-password` | 비밀번호 변경 |

#### 사용자 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/user/register` | 회원가입 / 추가정보입력 |
| POST | `/user/login` | 로그인 |
| PATCH | `/user/update` | 정보 수정 |
| POST | `/user/change-password` | 비밀번호 변경 |
| POST | `/user/withdraw` | 회원 탈퇴 |

#### 이음 채팅 API 🆕

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/eum-chat/list/{userId}` | 이음 채팅 목록 조회 |
| POST | `/eum-chat/today/{userId}` | 오늘의 채팅방 조회/생성 |
| POST | `/eum-chat/message` | 메시지 저장 (AI/사용자) |
| POST | `/eum-chat/emotion` | 감정 점수 저장 |
| DELETE | `/eum-chat/{eumChatId}` | 채팅방 삭제 |

#### 일반 채팅 API 🆕

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/chat/list/{userId}` | 일반 채팅 목록 조회 |
| POST | `/chat/create/{userId}` | 새 채팅방 생성 |
| POST | `/chat/message` | 메시지 저장 |
| DELETE | `/chat/{chatRoomId}` | 채팅방 삭제 |

#### 홈 화면 API 🆕

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/home/greeting/{userId}` | 인사말 + 실시간 날씨 조회 |
| POST | `/home/mood` | 오늘의 기분 저장 |

#### OAuth2

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/oauth2/authorization/google` | 구글 로그인 시작 |

### 상세 API 문서

Swagger UI에서 확인: http://localhost:8080/swagger-ui/index.html

---

## 🗄 데이터베이스 스키마

### 주요 테이블
```
pj_group              - 조직/그룹
pj_admin              - 관리자 계정
pj_admin_auth         - 관리자 권한
pj_user               - 사용자 계정
pj_user_option        - 사용자 설정
pj_user_chat_list     - 일반 채팅방 🆕
pj_user_chat_content  - 일반 채팅 내용 🆕
pj_eum_chat_list      - 이음(AI) 채팅방 🆕
pj_eum_chat_content   - 이음(AI) 채팅 내용 🆕
pj_user_emotion       - 감정 분석 결과 🆕
pj_user_life_data     - 사용자 일일 상태 🆕
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

### OpenWeatherMap API 설정

1. [OpenWeatherMap](https://openweathermap.org/) 접속
2. 무료 회원가입
3. API Keys 메뉴에서 키 발급
4. application.properties에 추가
5. 무료 플랜: 분당 60회, 일일 1,000,000회 호출 가능

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
│   │   │   ├── restTemplateConfig.java 🆕
│   │   │   └── oAuth2SuccessHandler.java
│   │   ├── controller/          # REST API 컨트롤러
│   │   │   ├── adminController.java
│   │   │   ├── userController.java
│   │   │   ├── eumChatController.java 🆕
│   │   │   ├── chatController.java 🆕
│   │   │   └── homeController.java 🆕
│   │   ├── service/             # 비즈니스 로직 (인터페이스)
│   │   │   ├── adminService.java
│   │   │   ├── userService.java
│   │   │   ├── eumChatService.java 🆕
│   │   │   ├── chatService.java 🆕
│   │   │   ├── homeService.java 🆕
│   │   │   ├── weatherService.java 🆕
│   │   │   └── customOAuth2UserService.java
│   │   ├── service/impl/        # 비즈니스 로직 (구현체)
│   │   │   ├── adminServiceImpl.java
│   │   │   ├── userServiceImpl.java
│   │   │   ├── eumChatServiceImpl.java 🆕
│   │   │   ├── chatServiceImpl.java 🆕
│   │   │   ├── homeServiceImpl.java 🆕
│   │   │   └── weatherServiceImpl.java 🆕
│   │   ├── mapper/              # MyBatis 매퍼
│   │   │   ├── adminMapper.java
│   │   │   ├── userMapper.java
│   │   │   ├── eumChatMapper.java 🆕
│   │   │   ├── chatMapper.java 🆕
│   │   │   └── homeMapper.java 🆕
│   │   ├── dto/                 # 데이터 전송 객체
│   │   │   ├── adminVO.java
│   │   │   ├── userVo.java
│   │   │   ├── loginRequest.java
│   │   │   ├── loginResponse.java
│   │   │   ├── userRegisterRequest.java
│   │   │   ├── adminRegisterRequest.java
│   │   │   ├── passwordChangeRequest.java
│   │   │   ├── userWithdrawRequest.java
│   │   │   ├── oAuth2UserInfo.java
│   │   │   ├── eumChatListResponse.java 🆕
│   │   │   ├── eumMessageSaveRequest.java 🆕
│   │   │   ├── emotionScoreSaveRequest.java 🆕
│   │   │   ├── chatListResponse.java 🆕
│   │   │   ├── chatMessageSaveRequest.java 🆕
│   │   │   ├── homeGreetingResponse.java 🆕
│   │   │   ├── todayMoodRequest.java 🆕
│   │   │   ├── weatherInfo.java 🆕
│   │   │   └── openWeatherResponse.java 🆕
│   │   └── PjEumApplication.java
│   └── resources/
│       ├── mapper/              # MyBatis XML
│       │   ├── adminMapper.xml
│       │   ├── userMapper.xml
│       │   ├── eumChatMapper.xml 🆕
│       │   ├── chatMapper.xml 🆕
│       │   └── homeMapper.xml 🆕
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
  - ✅ `eumChatService.java`

- **클래스명**: PascalCase
  - ✅ `adminVO`
  - ✅ `userRegisterRequest`
  - ✅ `weatherInfo`

- **메서드명**: camelCase
  - ✅ `findByEmail()`
  - ✅ `updateUserInfo()`
  - ✅ `getTodayEumChat()`

- **Service 패턴**: Interface + Impl
  - ✅ `eumChatService.java` (인터페이스)
  - ✅ `eumChatServiceImpl.java` (구현체)

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

## 📅 개발 로그

### 2024.11.23 🆕
- ✅ 이음 채팅 API 개발 (하루 단위 자동 생성)
- ✅ 일반 채팅 API 개발 (자유 생성)
- ✅ 홈 화면 API 개발 (인사말, 날씨, 기분 저장)
- ✅ OpenWeatherMap API 연동
- ✅ Service 레이어를 Interface + Impl 패턴으로 리팩토링
- ✅ MyBatis Mapper XML 추가 (userMapper, eumChatMapper, chatMapper, homeMapper)
- ✅ 감정 점수 저장 API 구현
- ✅ 채팅 목록 미리보기 기능 (10글자 + ...)

---


**Made with ❤️ by 이음 개발팀**
