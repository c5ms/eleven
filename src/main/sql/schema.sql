drop user if exists 'eleven'@'%';
create user 'eleven'@'%' identified by 'eleven';

-- 创建开发环境
drop database if exists eleven_upms;
create database eleven_upms collate utf8mb4_bin;
GRANT all on eleven_upms.* TO 'eleven'@'%';


create table eleven_upms.upms_access_token
(
    token_         varchar(100) null primary key,
    client_ip_     varchar(50)  null,
    create_at_     datetime     null,
    expire_at_     datetime     null,
    issuer         varchar(255) null,
    principal_name varchar(200) null,
    principal_type varchar(200) null
);

create table eleven_upms.upms_role
(
    id_      varchar(100) not null primary key,
    name_    varchar(100) not null unique,
    version_ int          null
);

create table eleven_upms.upms_user
(
    id_          varchar(100) not null primary key,
    from_id_     varchar(100) null unique,
    login_       varchar(100) null unique,
    nickname_    varchar(100) null,
    password_    varchar(100) null,
    state_       varchar(100) not null,
    type_        varchar(50)  null,
    _version     int          null,
    _create_by   varchar(100) null,
    _update_by   varchar(100) null,
    _create_date datetime     null,
    _update_date datetime     null
);

create index idx_user_from_id_ on eleven_upms.upms_user (from_id_);

create index idx_user_type_ on eleven_upms.upms_user (type_);

create table eleven_upms.upms_user_authority
(
    id_      varchar(100) not null primary key,
    user_id_ varchar(100) not null,
    type_    varchar(100) not null,
    name_    varchar(100) null,
    _version varchar(100) null,
    constraint upms_user_authority_unique unique (type_, user_id_, name_)
);

