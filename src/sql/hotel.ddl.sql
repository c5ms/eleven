create table hotel.hotel
(
    id                   varchar(50) not null,
    sale_state           varchar(20),
    hotel_name           varchar(100),
    contact_tel          varchar(20),
    contact_email        varchar(100),
    hotel_description    varchar(255),
    hotel_head_pic_url   varchar(255),
    hotel_room_number    integer,
    position_province    varchar(100),
    position_city        varchar(100),
    position_district    varchar(100),
    position_street      varchar(100),
    position_address     varchar(100),
    create_at            timestamp,
    create_by            varchar(100),
    update_at            timestamp,
    update_by            varchar(100),
    hotel_check_in_time  time,
    hotel_check_out_time time,
    position_lat         real,
    position_lng         real
);

alter table hotel.hotel
    owner to root;

create unique index hotel_pkey
    on hotel.hotel (id);

create unique index hotel_pk
    on hotel.hotel (id);

alter table hotel.hotel
    add primary key (id);

create table hotel.plan
(
    id                  varchar(50) not null,
    hotel_id            varchar(50)
        constraint plan_hotel_id_fk
            references hotel.hotel
            on delete cascade,
    name                varchar(100),
    "desc"              varchar(255),
    count               integer,
    sale_type           varchar(20),
    sale_state          varchar(20),
    sell_start_date     timestamp,
    sell_end_date       timestamp,
    stay_start_date     date,
    stay_end_date       date,
    pre_sell_start_date timestamp,
    pre_sell_end_date   timestamp,
    sell_state          varchar(20)
);

alter table hotel.plan
    owner to root;

create unique index plan_pkey
    on hotel.plan (id);

create unique index plan_pk
    on hotel.plan (id);

alter table hotel.plan
    add primary key (id);

create table hotel.admin
(
    id       varchar(50) not null,
    hotel_id varchar(50)
        constraint admin_hotel_id_fk
            references hotel.hotel
            on delete cascade,
    name     varchar(100),
    tel      varchar(20),
    email    varchar(100)
);

alter table hotel.admin
    owner to root;

create unique index admin_pkey
    on hotel.admin (id);

create unique index admin_pk
    on hotel.admin (id);

alter table hotel.admin
    add primary key (id);

create table hotel.register
(
    id            varchar(50) not null
        primary key,
    state         varchar(50),
    hotel_name    varchar(100),
    hotel_address varchar(100),
    manager_name  varchar(100),
    manager_tel   varchar(40),
    manager_email varchar(100),
    hotel_id      varchar(50)
        constraint register_hotel_hotel_id_fk
            references hotel.hotel
            on delete cascade
);

alter table hotel.register
    owner to root;

create unique index register_pk
    on hotel.register (id);

create table hotel.room
(
    id         varchar(50) not null
        primary key,
    hotel_id   varchar(50)
        constraint hotel_room_hotel_hotel_id_fk
            references hotel.hotel
            on delete cascade,
    name       varchar(100),
    "desc"     varchar(255),
    size       varchar(20),
    count      integer,
    sale_state varchar(20)
);

alter table hotel.room
    owner to root;

create unique index room_pk
    on hotel.room (id);

create table hotel.plan_room
(
    hotel_id varchar(50)
        constraint plan_room_hotel_id_fk
            references hotel.hotel
            on delete cascade,
    plan_id  varchar(50)
        constraint plan_room_plan_id_fk
            references hotel.plan
            on delete cascade,
    room_id  varchar(50)
        constraint plan_room_room_id_fk
            references hotel.room
            on delete cascade,
    count    integer,
    price    integer
);

alter table hotel.plan_room
    owner to root;

