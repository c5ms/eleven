
-- 访问令牌
create table upms_access_token
(
    token          varchar(100) not null primary key,
    client_ip      varchar(50)  not null,
    create_at      datetime     not null,
    expire_at      datetime     not null,
    issuer         varchar(255) not null,
    principal_name varchar(200) not null,
    principal_type varchar(200) not null
);

-- 角色
create table upms_role
(
    id      varchar(100) not null primary key,
    name    varchar(100) not null unique,
    version int          not null
);

-- 用户
create table upms_user
(
    id          varchar(100) not null primary key,
    login       varchar(100) null unique,
    nickname    varchar(100) not null,
    password    varchar(100) null,
    state       varchar(100) not null,
    type        varchar(50)  not null,
    version     int          not null,
    create_by   varchar(100) not null,
    update_by   varchar(100) not null,
    create_date datetime     not null,
    update_date datetime     not null
);

create index idx_user_type on upms_user (type);

-- 用户权限
create table upms_user_authority
(
    id      varchar(100) not null primary key,
    user_id varchar(100) not null,
    type    varchar(100) not null,
    name    varchar(100) not null,
    version varchar(100) not null,
    constraint upms_user_authority_unique unique (user_id, type, name)
);
