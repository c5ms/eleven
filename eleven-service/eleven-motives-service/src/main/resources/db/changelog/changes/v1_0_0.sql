create table finance_purchase_cost
(
    cost_id             bigint auto_increment primary key,
    purchase_cost       double null,
    purchase_order_id   bigint null,
    transportation_cost double null
);

create table inventory
(
    inventory_id      bigint auto_increment primary key,
    current_quantity  int    not null,
    product_id        bigint not null,
    safety_stock      int    not null,
    is_low            bit    not null,
    version           bigint not null,
    material_id       bigint not null,
    last_operate_date date   null,
    operate_version   bigint not null,
    schema_version    bigint not null
);

alter table inventory
    add constraint inventory_uk_01 unique (product_id);

create table inventory_transaction
(
    transaction_id    bigint auto_increment primary key,
    operate_date      date         null,
    product_id        bigint       null,
    purchase_order_id int          null,
    quantity          int          null,
    type              varchar(100) null,
    material_id       bigint       null
);

create table material
(
    material_id       bigint auto_increment primary key,
    create_date       date         null,
    main_supplier_id  varchar(255) null,
    material_category varchar(100) not null,
    material_code     varchar(255) not null,
    material_name     varchar(255) not null,
    safety_stock      double       null,
    unit              varchar(255) not null,
    detail_id         bigint       null
);

alter table material
    add constraint material_uk_02 unique (material_code);

create table material_detail
(
    detail_id                   bigint auto_increment primary key,
    color_fastness_grade        varchar(255) null,
    environmental_certification varchar(255) null,
    fabric_weight               double       null,
    material_composition        varchar(255) null,
    shrinkage_rate              double       null,
    tensile_strength            double       null
);

ALTER table material
    add constraint material_fk_01 foreign key (detail_id) references material_detail (detail_id);


create table purchase_order
(
    order_id     bigint auto_increment primary key,
    order_date   date         not null,
    order_number varchar(255) not null,
    status       varchar(255) not null,
    supplier_id  bigint       not null,
    amount       double       not null,
    ad_create_at datetime(6)  null,
    ad_create_by varchar(255) null,
    ad_update_at datetime(6)  null,
    ad_update_by varchar(255) null
);
alter table purchase_order
    add constraint purchase_order_uk_01 unique (order_number);

create table purchase_order_item
(
    item_id           bigint auto_increment primary key,
    price             double       not null,
    quantity          int          not null,
    purchase_order_id bigint       not null,
    material_code     varchar(255) not null,
    material_id       bigint       null
);

alter table purchase_order_item
    add constraint purchase_order_item_fk_01 foreign key (purchase_order_id) references purchase_order (order_id);

