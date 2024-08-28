CREATE TABLE if not exists member
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(30)  NOT NULL,
    provider          VARCHAR(30)  NOT NULL,
    identifier        VARCHAR(500) NOT NULL,
    nickname          VARCHAR(30)  NOT NULL UNIQUE,
    email             VARCHAR(50)  NOT NULL,
    picture           VARCHAR(255),
    privacy_agreed    BOOLEAN      NOT NULL,
    privacy_agreed_at DATETIME,
    score             DOUBLE       NOT NULL,
    birth             DATETIME,
    role              VARCHAR(20)  NOT NULL,
    created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (provider, identifier),
    unique (nickname)
);

CREATE TABLE if not exists match_game
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id       VARCHAR(20) unique,
    game_creation VARCHAR(100),
    game_duration BIGINT,
    game_mode     VARCHAR(100),
    unique (game_id)
);

CREATE TABLE if not exists match_user
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id       VARCHAR(20) not null,
    puuid         VARCHAR(100),
    nickname      VARCHAR(50),
    champion_name VARCHAR(30),
    win           BOOLEAN
);

CREATE TABLE if not exists post
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id     BIGINT,
    video_url     VARCHAR(255),
    thumbnail_url VARCHAR(255),
    title         VARCHAR(50),
    content       TEXT,
    view_count    BIGINT   DEFAULT 0,
    comment_count BIGINT   DEFAULT 0,
    vote_count    BIGINT   DEFAULT 0,
    vote_ratio    DOUBLE   DEFAULT 0.0,
    tags          JSON,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_vote_ratio_vote_count (vote_ratio DESC, vote_count DESC),
    INDEX idx_post_created_at_desc (created_at DESC),
    INDEX idx_view_count (view_count DESC)
);

CREATE TABLE if not exists vote_option
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id       BIGINT,
    match_user_id BIGINT
);

CREATE TABLE if not exists vote
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    option_id BIGINT,
    member_id BIGINT,
    post_id   BIGINT,
    UNIQUE (member_id, post_id) -- 중복 투표 방지
);

CREATE TABLE if not exists comment
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id  BIGINT,
    post_id    BIGINT NOT NULL,
    member_id  BIGINT NOT NULL,
    content    TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE if not exists member_ban -- 사용자 차단
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT,
    banned_member_id BIGINT,
    UNIQUE (member_id, banned_member_id), -- 중복 차단 방지
    INDEX idx_member_ban_member_id (member_id)
);

CREATE TABLE if not exists post_ban
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id   BIGINT,
    ban_post_id BIGINT,
    UNIQUE (member_id, ban_post_id), -- 중복 차단 방지
    INDEX idx_post_ban_member_id (member_id)
);

CREATE TABLE if not exists post_view
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_post_view_created_at_post_id (created_at, post_id)
);

CREATE TABLE if not exists post_view
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    INDEX idx_post_view_created_at_post_id (created_at DESC, post_id)
);
