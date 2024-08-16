create table users(
    id bigserial primary key,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email varchar(30)
);

create table roles(
    id serial,
    name varchar(50) not null,
    primary key (id)
);

create table authorities(
    id serial,
    name varchar(50) not null,
    primary key (id)
);

create table users_roles(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users(id),
    foreign key (role_id) references roles(id)
);

create table users_authorities(
    user_id bigint not null,
    authority_id bigint not null,
    primary key (user_id, authority_id),
    foreign key (user_id) references users(id),
    foreign key (authority_id) references authorities(id)
);

insert into roles(name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into authorities(name) VALUES
                                        ('READ'),
                                        ('UPDATE'),
                                        ('DELETE');

insert into users(username, password, email) VALUES
                                                 ('user1', '$2a$12$k08wzrDYrW4fyEaViMgYG.j8Ge2.0Y1f9Efp/fVIgt5mj4fJk.Vly', 'user1@gmail.com'),
                                                 ('admin', '$2a$12$k08wzrDYrW4fyEaViMgYG.j8Ge2.0Y1f9Efp/fVIgt5mj4fJk.Vly', 'admin@gmail.com'),
                                                 ('superadmin', '$2a$12$k08wzrDYrW4fyEaViMgYG.j8Ge2.0Y1f9Efp/fVIgt5mj4fJk.Vly', 'superadmin@gmail.com'),
                                                 ('user2', '$2a$12$k08wzrDYrW4fyEaViMgYG.j8Ge2.0Y1f9Efp/fVIgt5mj4fJk.Vly', 'user2@gmail.com'),
                                                 ('user3', '$2a$12$k08wzrDYrW4fyEaViMgYG.j8Ge2.0Y1f9Efp/fVIgt5mj4fJk.Vly', 'user3@gmail.com');

insert into users_roles(user_id, role_id) VALUES
                                              (1, 1),
                                              (2, 2),
                                              (3, 2),
                                              (4, 1),
                                              (5, 1);

insert into users_authorities(user_id, authority_id) VALUES
                                                         (1, 1),
                                                         (5, 1),
                                                         (2, 1), (2, 2),
                                                         (3, 1), (3, 2), (3, 3);