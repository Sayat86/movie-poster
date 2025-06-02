create table endpoints
(
    id serial primary key,
    app varchar(255) not null,
    uri varchar(255) not null,
    unique(app, uri)
);

create table hits
(
    id serial primary key,
    ip varchar(15) not null,
    timestamp timestamp without time zone not null,
    endpoint_id int references endpoints(id)
);