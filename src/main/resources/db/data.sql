insert into user_tb(username, password, email) values('ssar', '$2a$10$.XcJk7Z4H95gvjCgkKaSp.YDzFTlx7hu6hvm9/arAXH7NR3xPQxM2', 'ssar@nate.com');
insert into user_tb(username, password, email) values('cos', '$2a$10$PT2dmjG/AOlECV5hBL5Fou3e/LkCMFo/xHgjYE0ydj5MQDfvxt3O6', 'cos@nate.com');
insert into user_tb(username, password, email) values('love', '$2a$10$PT2dmjG/AOlECV5hBL5Fou3e/LkCMFo/xHgjYE0ydj5MQDfvxt3O6', 'love@nate.com');
insert into board_tb(title, content, user_id, created_at) values('안녕', '만나서 반가워', 1, now());
insert into board_tb(title, content, user_id, created_at) values('나도', '만나서 반가워', 2, now());
insert into board_tb(title, content, user_id, created_at) values('내이름은', 'ssar이야', 1, now());
insert into board_tb(title, content, user_id, created_at) values('나는', 'cos야', 2, now());
insert into board_tb(title, content, user_id, created_at) values('나는', 'love야', 3, now());
insert into board_tb(title, content, user_id, created_at) values('나도', '같이 놀자', 3, now());
insert into reply_tb(comment, board_id, user_id) values('댓글1', 4, 1);
insert into reply_tb(comment, board_id, user_id) values('댓글2', 4, 2);
insert into reply_tb(comment, board_id, user_id) values('댓글3', 4, 1);
insert into reply_tb(comment, board_id, user_id) values('댓글4', 4, 2);
insert into reply_tb(comment, board_id, user_id) values('댓글5', 4, 1);
insert into reply_tb(comment, board_id, user_id) values('댓글6', 4, 2);

