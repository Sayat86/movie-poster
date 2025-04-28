drop table if exists requests;
drop table if exists compilation_events;
drop table if exists compilations;
drop table if exists events;
drop table if exists categories;
drop table if exists users;

create table users
(
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique
);

create table categories
(
    id serial primary key,
    name varchar(255) not null unique
);

create table events
(
    id serial primary key,
    title varchar(255) not null,
    annotation varchar(255) not null,
    description varchar(255) not null,
    event_date timestamp with time zone not null,
    created_on timestamp with time zone not null,
    published_on timestamp with time zone,
    paid boolean not null,
    participant_limit int not null,
    request_moderation boolean not null,
    state varchar(50) not null,
    location_lat double precision not null,
    location_lon double precision not null,
    confirmed_requests int not null,
    views int not null,
    is_published boolean not null,
    category_id int references categories(id) not null,
    initiator_id int references users(id)
);

create table compilations
(
    id serial primary key,
    title varchar(255) not null,
    pinned boolean not null
);

create table compilation_events
(
    compilation_id int references compilations(id),
    event_id int references events(id)
);

create table requests
(
    id serial primary key,
    created timestamp with time zone not null,
    status varchar(50) not null,
    event_id int references events(id),
    requester_id int references users(id)
);