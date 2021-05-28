SET FOREIGN_KEY_CHECKS = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO blog_post(id, title, content)
VALUES(41, 'Title post 1', 'Post comment 1'),
             (42, 'Title post 2', 'Post comment 2'),
             (43, 'Title post 3', 'Post comment 3'),
             (44, 'Title post 4', 'Post comment 4'),
             (45, 'Title post 5', 'Post comment 5');

SET FOREIGN_KEY_CHECKS = 1;
