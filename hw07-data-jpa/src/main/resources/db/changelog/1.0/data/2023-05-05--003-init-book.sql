insert into book (name, release_year, author_id)
values ('Game of Thrones', 1996, 1),
       ('The Idiot', 1868, 2),
       ('The Brothers Karamazov', 1879, 2);

insert into book_genre (book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (3, 3);