create table doney_issue
(
    id               varchar(50)   not null
        primary key,
    create_at        datetime(6)   not null comment '创建时间',
    create_by        varchar(100)  not null comment '创建人',
    update_at        datetime(6)   null comment '更新时间',
    update_by        varchar(100)  null comment '更新人',
    delete_at        datetime(3)   null comment '删除时间',
    version          int           null,
    project_id       varchar(50)   null,
    title            varchar(200)  not null,
    description      varchar(1024) null,
    state            varchar(50)   not null,
    last_handle_time datetime      null
);

create table doney_member
(
    id         varchar(50)  not null
        primary key,
    create_at  datetime(6)  not null comment '创建时间',
    create_by  varchar(100) not null comment '创建人',
    update_at  datetime(6)  null comment '更新时间',
    update_by  varchar(100) null comment '更新人',
    delete_at  datetime(3)  null comment '删除时间',
    version    int          null,
    user_id    varchar(50)  not null,
    project_id varchar(50)  not null,
    constraint idx_doney_member_001
        unique (project_id, user_id)
);

create table doney_member_role
(
    `key`      varchar(50) null,
    member_id  varchar(50) not null,
    user_id    varchar(50) not null,
    project_id varchar(50) not null,
    name       varchar(50) not null,
    primary key (member_id, name)
);

create table doney_project
(
    id          varchar(50)   not null
        primary key,
    create_at   datetime(6)   not null comment '创建时间',
    create_by   varchar(100)  not null comment '创建人',
    update_at   datetime(6)   null comment '更新时间',
    update_by   varchar(100)  null comment '更新人',
    delete_at   datetime(3)   null comment '删除时间',
    version     int           null,
    title       varchar(200)  not null,
    code        varchar(100)  not null,
    description varchar(1024) null,
    state       varchar(50)   not null,
    url         varchar(500)  null
);

create table formula
(
    id              bigint auto_increment
        primary key,
    appointmentDate datetime(6)                            null,
    name            varchar(255)                           null,
    running         bit                                    null,
    state           enum ('APPROVING', 'PASSED', 'REJECT') null,
    stopAt          datetime(6)                            null
);

create table formula_examination
(
    id          bigint auto_increment
        primary key,
    examineDate datetime(6)                            null,
    examiner    varchar(255)                           null,
    state       enum ('APPROVING', 'PASSED', 'REJECT') null
);

create table gate_route
(
    id         varchar(60)  not null comment '物理主键'
        primary key,
    is_started tinyint(1)   null,
    memo       varchar(100) null,
    method     varchar(100) null,
    name       varchar(100) null,
    `order`    bigint       null,
    path       varchar(100) null,
    create_at  datetime(3)  not null comment '创建时间',
    create_by  varchar(100) not null comment '创建人',
    update_at  datetime(3)  null comment '更新时间',
    update_by  varchar(100) null comment '更新人',
    delete_at  datetime(3)  null comment '删除时间',
    version    bigint       not null comment '版本号',
    target     json         null
);

create identity idx_gate_route_delete_at
    on gate_route (delete_at);

create table gate_site
(
    name       varchar(100) null,
    memo       varchar(100) null,
    `order`    bigint       null,
    is_started tinyint(1)   null,
    delete_at  datetime(3)  null comment '删除时间',
    version    bigint       not null comment '版本号',
    id         varchar(60)  not null comment '物理主键'
        primary key,
    create_at  datetime(3)  not null comment '创建时间',
    create_by  varchar(100) not null comment '创建人',
    update_at  datetime(3)  null comment '更新时间',
    update_by  varchar(100) null comment '更新人'
);

create identity idx_gate_site_delete_at
    on gate_site (delete_at);

create table upms_access_token
(
    token          varchar(60)  not null
        primary key,
    create_at      datetime(3)  null,
    expire_at      datetime(3)  null,
    client_ip      varchar(50)  null,
    issuer         varchar(200) null,
    principal_name varchar(100) null,
    principal_type varchar(100) null
);

create identity upms_access_token_principal_name_principal_type_index
    on upms_access_token (principal_name, principal_type);

create table upms_authority
(
    id         varchar(100) not null
        primary key,
    owner_name varchar(100) not null,
    owner_type varchar(100) not null,
    power_type varchar(100) not null,
    power_name varchar(100) not null,
    create_at  datetime(6)  not null comment '创建时间',
    create_by  varchar(100) not null comment '创建人',
    update_at  datetime(6)  null comment '更新时间',
    update_by  varchar(100) null comment '更新人'
);

create identity idx_upms_authority_01
    on upms_authority (owner_type, owner_name);

create identity idx_upms_authority_02
    on upms_authority (power_type, power_name);

create table upms_role
(
    id        varchar(100) not null
        primary key,
    code      varchar(50)  not null,
    name      varchar(100) not null,
    create_at datetime(3)  not null comment '创建时间',
    create_by varchar(100) not null comment '创建人',
    update_at datetime(3)  null comment '更新时间',
    update_by varchar(100) null comment '更新人',
    constraint code
        unique (code)
);

create table upms_user
(
    id          varchar(50)  not null comment '物理主键'
        primary key,
    username    varchar(100) null,
    nickname    varchar(100) null,
    password    varchar(100) null,
    login_at    datetime(3)  null,
    state       varchar(100) null,
    is_locked   tinyint(1)   null,
    type        varchar(50)  null,
    register_at datetime(3)  null,
    create_at   datetime(6)  not null comment '创建时间',
    create_by   varchar(100) not null comment '创建人',
    update_at   datetime(6)  null comment '更新时间',
    update_by   varchar(100) null comment '更新人',
    delete_at   datetime(3)  null comment '删除时间',
    version     int          null,
    create_date datetime(6)  null,
    login       varchar(100) null,
    update_date datetime(6)  null
);

create identity idx_upms_user_delete_at
    on upms_user (delete_at);

create identity idx_upms_user_deleted_at
    on upms_user (delete_at);

