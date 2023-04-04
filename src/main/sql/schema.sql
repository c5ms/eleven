drop user if exists 'eleven'@'%';
create user 'eleven'@'%' identified by 'eleven';

-- 创建开发环境
drop database if exists eleven_upms;
create database eleven_upms collate utf8mb4_bin;
GRANT all on * TO 'eleven'@'%';

--
use eleven_upms;

-- 创建数据库表

create table upms_access_token
(
    token_         varchar(100) not null primary key,
    client_ip_     varchar(50)  not null,
    create_at_     datetime     not null,
    expire_at_     datetime     not null,
    issuer         varchar(255) not null,
    principal_name varchar(200) not null,
    principal_type varchar(200) not null
);

create table upms_role
(
    id_      varchar(100) not null primary key,
    name_    varchar(100) not null unique,
    version_ int          not null
);

create table upms_user
(
    id_          varchar(100) not null primary key,
    from_id_     varchar(100) null unique,
    login_       varchar(100) null unique,
    nickname_    varchar(100) not null,
    password_    varchar(100) null,
    state_       varchar(100) not null,
    type_        varchar(50)  not null,
    _version     int          not null,
    _create_by   varchar(100) not null,
    _update_by   varchar(100) not null,
    _create_date datetime     not null,
    _update_date datetime     not null
);

create index idx_user_from_id_ on upms_user (from_id_);

create index idx_user_type_ on upms_user (type_);

create table upms_user_authority
(
    id_      varchar(100) not null primary key,
    user_id_ varchar(100) not null,
    type_    varchar(100) not null,
    name_    varchar(100) not null,
    _version varchar(100) not null,
    constraint upms_user_authority_unique unique (user_id_, type_, name_)
);