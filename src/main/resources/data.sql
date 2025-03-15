-- 1. Chèn dữ liệu vào tbl_user
-- Mật khẩu: 123456
INSERT INTO tbl_user (activation_key, created_at, description, email, password, username)
VALUES ('', '2025-03-14 15:00:00', 'User one description', 'user1@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user1'),
       ('', '2025-03-14 15:05:00', 'User two description', 'user2@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user2'),
       ('', '2025-03-14 15:10:00', 'User three description', 'user3@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user3');

-- 2. Chèn dữ liệu vào tbl_group
INSERT INTO tbl_group (access, created_at, description, name, type, created_by_id)
VALUES ('PUBLIC', '2025-03-14 15:20:00', 'Group description 1', 'Group1', 'TypeA', 1),
       ('PRIVATE', '2025-03-14 15:25:00', 'Group description 2', 'Group2', 'TypeB', 2);

-- 3. Chèn dữ liệu vào tbl_board
INSERT INTO tbl_board (created_at, description, name, title, created_by, group_id)
VALUES ('2025-03-14 15:30:00', 'Board description 1', 'Board1', 'Title1', 1, 1),
       ('2025-03-14 15:35:00', 'Board description 2', 'Board2', 'Title2', 2, 2);

-- 4. Chèn dữ liệu vào tbl_list
INSERT INTO tbl_list (name, priority, board_id)
VALUES ('List1', 1, 1),
       ('List2', 2, 1),
       ('List3', 1, 2);

-- 5. Chèn dữ liệu vào tbl_card
INSERT INTO tbl_card (created_at, description, due_date, priority, title, created_by_id, list_id)
VALUES ('2025-03-14 15:40:00', 'Card description 1', '2025-03-20 12:00:00', 1, 'Card1', 1, 1),
       ('2025-03-14 15:45:00', 'Card description 2', '2025-03-21 12:00:00', 2, 'Card2', 2, 2),
       ('2025-03-14 15:50:00', 'Card description 3', '2025-03-22 12:00:00', 1, 'Card3', 3, 3);

-- 6. Không chèn dữ liệu vào tbl_card_file

-- 7. Chèn dữ liệu vào tbl_label
INSERT INTO tbl_label (color, title, board_id)
VALUES ('BLUE', 'Blue Label', 1),
       ('RED', 'Red Label', 1),
       ('GREEN', 'Green Label', 2);

-- 8. Chèn dữ liệu vào tbl_card_label
INSERT INTO tbl_card_label (board_id, color, card_id)
VALUES (1, 'BLUE', 1),
       (1, 'RED', 1),
       (1, 'BLUE', 2),
       (2, 'GREEN', 3);

-- 9. Chèn dữ liệu vào tbl_comment
INSERT INTO tbl_comment (created_at, description, card_id, created_by_id)
VALUES ('2025-03-14 15:55:00', 'Comment 1 on Card 1', 1, 2),
       ('2025-03-14 15:56:00', 'Comment 2 on Card 2', 2, 3),
       ('2025-03-14 15:57:00', 'Comment 3 on Card 1', 1, 3);

-- 10. Chèn dữ liệu vào tbl_group_member
INSERT INTO tbl_group_member (member_type, group_id, user_id)
VALUES ('MEMBER', 1, 2),
       ('MODERATOR', 1, 1),
       ('MEMBER', 2, 3);