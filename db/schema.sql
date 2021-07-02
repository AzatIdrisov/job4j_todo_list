create table tasks
(
    id          serial primary key,
    head        text,
    description text,
    created     timestamp,
    isdone      bool,
    user_id     int not null references users (id),
    category_id     int not null references categories (id)
);

create table users
(
    id      serial primary key,
    name    varchar(2000),
    email text,
    password text
);

create table categories
(
    id      serial primary key,
    name    varchar(2000)
);
