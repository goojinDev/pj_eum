-- ========================================
-- 이음(Eum) 프로젝트 - MariaDB DDL
-- 생성일: 2024-11-23
-- 데이터베이스: MariaDB 10.x 이상
-- ========================================

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS eum_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE eum_db;

-- ========================================
-- 1. 조직/그룹 테이블
-- ========================================

-- 조직 그룹
CREATE TABLE pj_group (
group_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '그룹 ID',
group_name VARCHAR(200) NOT NULL COMMENT '그룹명',
group_type VARCHAR(50) NOT NULL COMMENT '그룹 유형',
sido VARCHAR(50) NOT NULL COMMENT '시도',
sigungu VARCHAR(50) NOT NULL COMMENT '시군구',
address VARCHAR(500) COMMENT '주소',
contact_phone VARCHAR(20) COMMENT '연락처',
contact_email VARCHAR(200) COMMENT '이메일',
manager_name VARCHAR(100) COMMENT '담당자명',
group_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '그룹 상태',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='조직 그룹';

-- ========================================
-- 2. 관리자 관련 테이블
-- ========================================

-- 관리자 계정
CREATE TABLE pj_admin (
admin_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '관리자 ID',
admin_login_id VARCHAR(100) NOT NULL UNIQUE COMMENT '로그인 ID',
admin_pw VARCHAR(200) NOT NULL COMMENT '비밀번호',
admin_name VARCHAR(100) NOT NULL COMMENT '관리자명',
admin_email VARCHAR(200) NOT NULL UNIQUE COMMENT '이메일',
admin_phone VARCHAR(20) COMMENT '전화번호',
admin_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '상태',
group_id VARCHAR(50) COMMENT '그룹 ID',
last_login_date DATETIME COMMENT '마지막 로그인',
login_fail_count INT NOT NULL DEFAULT 0 COMMENT '로그인 실패 횟수',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (group_id) REFERENCES pj_group(group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='관리자 계정';

-- 관리자 권한
CREATE TABLE pj_admin_auth (
auth_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '권한 ID',
admin_id VARCHAR(50) NOT NULL COMMENT '관리자 ID',
authority VARCHAR(50) NOT NULL COMMENT '권한',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
FOREIGN KEY (admin_id) REFERENCES pj_admin(admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='관리자 권한';

-- ========================================
-- 3. 사용자 관련 테이블
-- ========================================

-- 사용자 계정
CREATE TABLE pj_user (
user_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '사용자 ID',
email VARCHAR(200) NOT NULL UNIQUE COMMENT '이메일',
user_pw VARCHAR(200) COMMENT '비밀번호',
user_name VARCHAR(100) NOT NULL COMMENT '사용자명',
nickname VARCHAR(100) COMMENT '닉네임',
profile_image VARCHAR(500) COMMENT '프로필 이미지',
login_type VARCHAR(20) NOT NULL DEFAULT 'LOCAL' COMMENT '로그인 유형',
provider_id VARCHAR(200) COMMENT '소셜 로그인 Provider ID',
user_role VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '역할',
user_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '상태',
group_id VARCHAR(50) COMMENT '그룹 ID',
birth_date DATE COMMENT '생년월일',
gender VARCHAR(10) COMMENT '성별',
phone VARCHAR(20) COMMENT '전화번호',
email_verified CHAR(1) NOT NULL DEFAULT 'N' COMMENT '이메일 인증 여부',
last_login_date DATETIME COMMENT '마지막 로그인',
login_fail_count INT NOT NULL DEFAULT 0 COMMENT '로그인 실패 횟수',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
background_theme VARCHAR(100) DEFAULT 'DEFAULT' COMMENT '배경 테마',
FOREIGN KEY (group_id) REFERENCES pj_group(group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자 계정';

-- 사용자 설정
CREATE TABLE pj_user_option (
option_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '설정 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
notification_enabled CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '알림 활성화',
email_notification CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '이메일 알림',
push_notification CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '푸시 알림',
eum_chat_enabled CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '이음 채팅 활성화',
eum_chat_time VARCHAR(5) DEFAULT '09:00' COMMENT '이음 채팅 시간',
theme VARCHAR(20) DEFAULT 'LIGHT' COMMENT '테마',
language VARCHAR(10) DEFAULT 'KO' COMMENT '언어',
data_sharing CHAR(1) NOT NULL DEFAULT 'N' COMMENT '데이터 공유 동의',
marketing_agree CHAR(1) NOT NULL DEFAULT 'N' COMMENT '마케팅 수신 동의',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자 설정';

-- 사용자 활동 로그
CREATE TABLE pj_user_activity (
activity_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '활동 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
activity_type VARCHAR(50) NOT NULL COMMENT '활동 유형',
activity_detail VARCHAR(200) COMMENT '활동 상세',
activity_data JSON COMMENT '활동 데이터',
ip_address VARCHAR(45) COMMENT 'IP 주소',
user_agent TEXT COMMENT 'User Agent',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자 활동 로그';

-- ========================================
-- 4. 일반 채팅 관련 테이블
-- ========================================

-- 일반 채팅 리스트
CREATE TABLE pj_user_chat_list (
chat_room_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '채팅방 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
room_title VARCHAR(200) COMMENT '채팅방 제목',
room_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '채팅방 상태',
last_message TEXT COMMENT '마지막 메시지',
last_message_date DATETIME COMMENT '마지막 메시지 시간',
message_count INT NOT NULL DEFAULT 0 COMMENT '메시지 수',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='일반 채팅 리스트';

-- 일반 채팅 내용
CREATE TABLE pj_user_chat_content (
message_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '메시지 ID',
chat_room_id VARCHAR(50) NOT NULL COMMENT '채팅방 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
message_type VARCHAR(10) NOT NULL COMMENT '메시지 유형 (USER/AI)',
message_content TEXT NOT NULL COMMENT '메시지 내용',
message_seq INT NOT NULL COMMENT '메시지 순서',
is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
FOREIGN KEY (chat_room_id) REFERENCES pj_user_chat_list(chat_room_id),
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='일반 채팅 내용';

-- 일반 채팅 매크로
CREATE TABLE pj_user_chat_macro (
macro_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '매크로 ID',
macro_type VARCHAR(20) NOT NULL COMMENT '매크로 유형',
macro_category VARCHAR(50) COMMENT '매크로 카테고리',
macro_title VARCHAR(200) NOT NULL COMMENT '매크로 제목',
macro_content TEXT NOT NULL COMMENT '매크로 내용',
macro_order INT DEFAULT 0 COMMENT '정렬 순서',
use_yn CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='일반 채팅 매크로';

-- ========================================
-- 5. 이음(AI) 채팅 관련 테이블
-- ========================================

-- 이음 채팅방
CREATE TABLE pj_eum_chat_list (
eum_chat_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '이음 채팅방 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
chat_date DATE NOT NULL COMMENT '채팅 날짜',
chat_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '채팅 상태',
is_completed CHAR(1) NOT NULL DEFAULT 'N' COMMENT '완료 여부',
message_count INT DEFAULT 0 COMMENT '메시지 수',
first_message_date DATETIME COMMENT '첫 메시지 시간',
last_message_date DATETIME COMMENT '마지막 메시지 시간',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id),
              UNIQUE KEY uk_user_chat_date (user_id, chat_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='이음 채팅방';

-- 이음 채팅 내용
CREATE TABLE pj_eum_chat_content (
eum_message_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '이음 메시지 ID',
eum_chat_id VARCHAR(50) NOT NULL COMMENT '이음 채팅방 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
message_type VARCHAR(10) NOT NULL COMMENT '메시지 유형 (AI/USER)',
message_content TEXT NOT NULL COMMENT '메시지 내용',
message_seq INT NOT NULL COMMENT '메시지 순서',
question_type VARCHAR(50) COMMENT '질문 유형',
is_deleted CHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
FOREIGN KEY (eum_chat_id) REFERENCES pj_eum_chat_list(eum_chat_id),
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='이음 채팅 내용';

-- ========================================
-- 6. 감정/상태 분석 관련 테이블
-- ========================================

-- 감정 분석 결과
CREATE TABLE pj_user_emotion (
emotion_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '감정 ID',
message_id VARCHAR(50) COMMENT '일반 메시지 ID',
eum_message_id VARCHAR(50) COMMENT '이음 메시지 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
emotion_type VARCHAR(20) COMMENT '감정 유형',
emotion_score INT COMMENT '감정 점수',
sentiment VARCHAR(10) COMMENT '감정 (긍정/부정)',
sentiment_score INT COMMENT '감정 점수',
keywords JSON COMMENT '키워드',
analysis_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '분석 일시',
model_version VARCHAR(20) COMMENT '모델 버전',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='감정 분석 결과';

-- 사용자 일일 상태
CREATE TABLE pj_user_life_data (
life_data_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '일일 데이터 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
status_date DATE NOT NULL COMMENT '상태 날짜',
eum_chat_id VARCHAR(50) COMMENT '이음 채팅방 ID',
total_score INT DEFAULT 0 COMMENT '총점',
status_text VARCHAR(100) COMMENT '상태 텍스트',
mood_score INT DEFAULT 0 COMMENT '기분 점수',
mood_level VARCHAR(20) COMMENT '기분 레벨',
sleep_score INT DEFAULT 0 COMMENT '수면 점수',
sleep_level VARCHAR(20) COMMENT '수면 레벨',
meal_score INT DEFAULT 0 COMMENT '식사 점수',
meal_level VARCHAR(20) COMMENT '식사 레벨',
exercise_score INT DEFAULT 0 COMMENT '운동 점수',
exercise_level VARCHAR(20) COMMENT '운동 레벨',
social_score INT DEFAULT 0 COMMENT '사회활동 점수',
social_level VARCHAR(20) COMMENT '사회활동 레벨',
goal_score INT DEFAULT 0 COMMENT '목표 점수',
goal_level VARCHAR(20) COMMENT '목표 레벨',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id),
UNIQUE KEY uk_user_status_date (user_id, status_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자 일일 상태';

-- ========================================
-- 7. 위험 관리 테이블
-- ========================================

-- 위험군 모니터링
CREATE TABLE pj_risk_monitoring (
monitoring_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '모니터링 ID',
user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
risk_level VARCHAR(20) NOT NULL COMMENT '위험 수준',
risk_score INT NOT NULL COMMENT '위험 점수',
detection_type VARCHAR(50) NOT NULL COMMENT '감지 유형',
detection_reason TEXT COMMENT '감지 사유',
detection_source VARCHAR(20) COMMENT '감지 출처',
action_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '조치 상태',
action_taken TEXT COMMENT '조치 내용',
action_date DATETIME COMMENT '조치 일시',
assigned_admin_id VARCHAR(50) COMMENT '담당 관리자 ID',
reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
upd_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
FOREIGN KEY (user_id) REFERENCES pj_user(user_id),
FOREIGN KEY (assigned_admin_id) REFERENCES pj_admin(admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='위험군 모니터링';

-- ========================================
-- 인덱스 생성
-- ========================================

-- 사용자 테이블 인덱스
CREATE INDEX idx_user_email ON pj_user(email);
CREATE INDEX idx_user_status ON pj_user(user_status);
CREATE INDEX idx_user_login_type ON pj_user(login_type);

-- 채팅 관련 인덱스
CREATE INDEX idx_user_chat_list_user ON pj_user_chat_list(user_id);
CREATE INDEX idx_user_chat_content_room ON pj_user_chat_content(chat_room_id);
CREATE INDEX idx_eum_chat_list_user ON pj_eum_chat_list(user_id);
CREATE INDEX idx_eum_chat_list_date ON pj_eum_chat_list(chat_date);
CREATE INDEX idx_eum_chat_content_chat ON pj_eum_chat_content(eum_chat_id);

-- 감정 분석 인덱스
CREATE INDEX idx_user_emotion_user ON pj_user_emotion(user_id);
CREATE INDEX idx_user_life_data_user ON pj_user_life_data(user_id);
CREATE INDEX idx_user_life_data_date ON pj_user_life_data(status_date);

-- 위험 모니터링 인덱스
CREATE INDEX idx_risk_monitoring_user ON pj_risk_monitoring(user_id);
CREATE INDEX idx_risk_monitoring_level ON pj_risk_monitoring(risk_level);
CREATE INDEX idx_risk_monitoring_status ON pj_risk_monitoring(action_status);

-- ========================================
-- 완료
-- ========================================