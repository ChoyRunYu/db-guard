create table if not exists datasource
(
    id               varchar(64) not null primary key,
    data_source_name varchar(100) not null,
    data_source_desc varchar(1000) not null,
    db_type          varchar(20) not null,
    db_host          varchar(20) not null,
    db_port          int          not null,
    username         varchar(50) not null,
    password         varchar(50) not null,
    db_name          varchar(50) not null,
    app_id           int
);