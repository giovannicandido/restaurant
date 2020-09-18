INSERT INTO roles(name) VALUES('ROLE_USER');
insert into users(email, password, username)
values ('user1@dbserver.com.br','$2a$10$sDnUOCj0HjyRTB8BjVASKu2XKcJ0AZAAHxNyLig4ijXfP1jQ6OEOa', 'user1');
insert into users(email, password, username)
values ('user2@dbserver.com.br','$2a$10$sDnUOCj0HjyRTB8BjVASKu2XKcJ0AZAAHxNyLig4ijXfP1jQ6OEOa', 'user2');
insert into user_roles(user_id, role_id) values ( 1, 1 );
insert into user_roles(user_id, role_id) values ( 2, 1 );

