create table tasks (
                       id serial primary key,
                       head text,
                       description text,
                       created timestamp,
                       isdone bool
);