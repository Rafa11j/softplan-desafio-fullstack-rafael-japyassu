insert into users
    (id, name, email, password, user_type, created_at, updated_at)
values
    ('c54aeb4b-2abe-49c0-9be4-1a217cf0a272', 'Administrador', 'admin@email.com',
        '$2a$10$x31WfVSETKIvHzDY2mvmbuQ6kqu7PUthrk9BgBUr0HaKVvsLtKnVK', 0, now(), now()),
    ('150938c3-3c98-44f6-a956-782e93047a98', 'Ana', 'ana@email.com',
        '$2a$10$joQTNQhLZwA4bR2UcT9nv.omIcnLOoe2KdlshbYXXz02bX7IVXM5G', 1, now(), now()),
    ('6f6b039d-d1ac-48f5-b256-1351ba67e02e', 'José', 'jose@email.com',
        '$2a$10$qqEJino6nN46xfo1IY8HGuc74sZ3aacgn7hyZ03VftfNJZ/R/ET2K', 2, now(), now());