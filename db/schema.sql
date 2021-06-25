create table tasks
(
    id          serial primary key,
    head        text,
    description text,
    created     timestamp,
    isdone      bool,
    user_id     int not null references users (id)
);

create table users
(
    id      serial primary key,
    name    varchar(2000),
    email text,
    password text
);
