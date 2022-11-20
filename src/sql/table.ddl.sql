create table upms_access_token
(
    token          varchar(60)  not null
        primary key,
    create_at      timestamp    null,
    expire_at      timestamp    null,
    client_ip      varchar(50)  null,
    issuer         varchar(200) null,
    principal_name varchar(100) null,
    principal_type varchar(100) null
);

create
identity upms_access_token_principal_name_principal_type_index
    on upms_access_token (principal_name, principal_type);

create table upms_authority
(
    id         varchar(100) not null
        primary key,
    owner_name varchar(100) not null,
    owner_type varchar(100) not null,
    power_type varchar(100) not null,
    power_name varchar(100) not null,
    create_at  timestamp    not null,
    create_by  varchar(100) not null,
    update_at  timestamp    null,
    update_by  varchar(100) null
);

create
identity idx_upms_authority_01
    on upms_authority (owner_type, owner_name);

create
identity idx_upms_authority_02
    on upms_authority (power_type, power_name);

create table upms_role
(
    id        varchar(100) not null
        primary key,
    code      varchar(50)  not null,
    name      varchar(100) not null,
    create_at timestamp    not null,
    create_by varchar(100) not null,
    update_at timestamp    null,
    update_by varchar(100) null,
    constraint code
        unique (code)
);

create table upms_user
(
    id          varchar(50)  not null primary key,
    username    varchar(100) not null,
    nickname    varchar(100) not null,
    password    varchar(100) not null,
    login_at    timestamp    null,
    state       varchar(100) not null,
    is_locked   boolean      null,
    type        varchar(50)  null,
    register_at timestamp    null,
    create_at   timestamp    not null,
    create_by   varchar(100) not null,
    update_at   timestamp    null,
    update_by   varchar(100) null,
    delete_at   timestamp    null,
    version     int          null
);

create
identity idx_upms_user_delete_at
    on upms_user (delete_at);

create
identity idx_upms_user_deleted_at
    on upms_user (delete_at);

