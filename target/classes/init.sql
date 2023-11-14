create table user
(   id       bigint auto_increment primary key,
    firstname     varchar(200) not null,
    lastname     varchar(200) not null,
    email     varchar(200) not null,
    password     varchar(200) not null,
    is_admin tinyint(1) default false
);