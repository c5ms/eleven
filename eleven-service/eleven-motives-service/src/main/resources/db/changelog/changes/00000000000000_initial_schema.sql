-- auto-generated definition
create table hotel2
(
  hotel_id            bigint auto_increment
        primary key,
  active              bit            null,
  addr_address        varchar(255)   null,
  addr_city           varchar(255)   null,
  addr_country        varchar(255)   null,
  addr_location       varchar(255)   null,
  addr_province       varchar(255)   null,
  building_area       int            null,
  hotel_description   varchar(255)   null,
  contact_email       varchar(255)   null,
  last_renovation     varbinary(255) null,
  hotel_name          varchar(255)   not null,
  contact_phone       varchar(255)   null,
  star_rating         int            null,
  total_room_quantity int            null,
  when_built          varbinary(255) null,
  check_in_time       time(6)        null,
  check_out_time      time(6)        null,
  position_lat        double         null,
  position_lng        double         null
);

