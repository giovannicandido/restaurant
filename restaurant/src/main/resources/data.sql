INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
insert into users(email, password, username)
values ('user1@dbserver.com.br','$2a$10$sDnUOCj0HjyRTB8BjVASKu2XKcJ0AZAAHxNyLig4ijXfP1jQ6OEOa', 'user1');
insert into users(email, password, username)
values ('user2@dbserver.com.br','$2a$10$sDnUOCj0HjyRTB8BjVASKu2XKcJ0AZAAHxNyLig4ijXfP1jQ6OEOa', 'user2');
insert into user_roles(user_id, role_id) values ( 1, 1 );
insert into user_roles(user_id, role_id) values ( 2, 1 );

insert into restaurant(name, description, img_url)
values ('test 1',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tellus cras adipiscing enim eu turpis egestas pretium aenean. Eu volutpat odio facilisis mauris sit amet massa vitae.',
        'https://media-cdn.tripadvisor.com/media/photo-s/18/1a/96/54/main-restaurant.jpg'
        );
insert into restaurant(name, description, img_url)
values ('test 2',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tellus cras adipiscing enim eu turpis egestas pretium aenean. Eu volutpat odio facilisis mauris sit amet massa vitae.',
        'https://media-cdn.tripadvisor.com/media/photo-s/18/1a/96/54/main-restaurant.jpg'
        );
insert into restaurant(name, description, img_url)
values ('test 3',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tellus cras adipiscing enim eu turpis egestas pretium aenean. Eu volutpat odio facilisis mauris sit amet massa vitae.',
        'https://media-cdn.tripadvisor.com/media/photo-s/18/1a/96/54/main-restaurant.jpg'
        );

insert into restaurant(name, description, img_url)
values ('test 4',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tellus cras adipiscing enim eu turpis egestas pretium aenean. Eu volutpat odio facilisis mauris sit amet massa vitae.',
        'https://media-cdn.tripadvisor.com/media/photo-s/18/1a/96/54/main-restaurant.jpg'
        );

