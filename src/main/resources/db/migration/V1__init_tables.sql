create table comment
(
    id         bigint auto_increment
        primary key,
    post_id    bigint                             not null,
    member_id  bigint                             not null,
    content    text                               null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    parent_id  bigint                             null
)
    engine = InnoDB;

create table match_game
(
    id            bigint auto_increment
        primary key,
    game_id       varchar(255) null,
    game_creation varchar(255) null,
    game_duration bigint       null,
    game_mode     varchar(255) null,
    constraint gameId
        unique (game_id)
)
    engine = InnoDB;

create table match_user
(
    id            bigint auto_increment
        primary key,
    game_id       varchar(255) not null,
    puuid         varchar(255) null,
    nickname      varchar(255) null,
    champion_name varchar(255) null,
    win           tinyint(1)   null
)
    engine = InnoDB;

create table member
(
    id                bigint auto_increment
        primary key,
    name              varchar(30)                        not null,
    provider          varchar(30)                        not null,
    identifier        varchar(500)                       not null,
    nickname          varchar(10)                        not null,
    email             varchar(50)                        not null,
    picture           varchar(255)                       null,
    privacy_agreed    tinyint(1)                         not null,
    privacy_agreed_at datetime                           null,
    score             double                             not null,
    birth             datetime                           null,
    role              varchar(20)                        not null,
    created_at        datetime default CURRENT_TIMESTAMP null,
    updated_at        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint UC_Nickname
        unique (nickname),
    constraint member_unique
        unique (identifier, provider)
)
    engine = InnoDB;

create table member_ban
(
    id            bigint auto_increment
        primary key,
    member_id     bigint null,
    ban_member_id bigint null,
    constraint member_id
        unique (member_id, ban_member_id)
)
    engine = InnoDB;

create index idx_member_ban_banned_member_id
    on member_ban (member_id);

create table post
(
    id            bigint auto_increment
        primary key,
    member_id     bigint                             null,
    video_url     varchar(255)                       null,
    thumbnail_url varchar(255)                       null,
    title         varchar(50)                        null,
    content       text                               null,
    view_count    bigint   default 0                 null,
    comment_count bigint   default 48                null,
    created_at    datetime default CURRENT_TIMESTAMP null,
    updated_at    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    vote_count    bigint   default 0                 null,
    tags          json                               null,
    vote_ratio    double                             null
)
    engine = InnoDB;

create table post_ban
(
    id          bigint auto_increment
        primary key,
    member_id   bigint null,
    ban_post_id bigint null,
    constraint member_id
        unique (member_id, ban_post_id)
)
    engine = InnoDB;

create index idx_post_ban_banned_member_id
    on post_ban (member_id);

create table post_view
(
    id         bigint auto_increment
        primary key,
    post_id    bigint                             null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    engine = InnoDB;

create index idx_post_view_created_at_post_id
    on post_view (created_at, post_id);

create table test
(
    id   int         null,
    name varchar(50) null
)
    engine = InnoDB;

create table user_test
(
    id   int          not null
        primary key,
    name varchar(100) null
)
    engine = InnoDB;

create table vote
(
    id              bigint auto_increment
        primary key,
    vote_options_id bigint                              not null,
    post_id         bigint                              null,
    member_id       bigint                              not null,
    created_at      timestamp default CURRENT_TIMESTAMP null,
    updated_at      timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint vote_unique_key
        unique (member_id, post_id)
)
    engine = InnoDB;

create index idx_created_at
    on vote (created_at);

create index idx_updated_at
    on vote (updated_at);

create table vote_option
(
    id            bigint auto_increment
        primary key,
    post_id       bigint null,
    match_user_id bigint null
)
    engine = InnoDB;

