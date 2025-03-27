-- 1. Chèn dữ liệu vào tbl_user
-- Mật khẩu: 123456
INSERT INTO tbl_user (activation_key, created_at, description, email, password, username)
VALUES ('', '2025-03-14 15:00:00', 'User A description', 'userA@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user A'),
       ('', '2025-03-14 15:05:00', 'User B description', 'userB@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user B'),
       ('', '2025-03-14 15:10:00', 'User C description', 'userC@example.com', '$2a$12$AUIuCvV0hUiJGhzZxivJiepajNv8UMnW9NMIbEbOkPRBr3CmwXVeK', 'user C');

-- 2. Chèn dữ liệu vào tbl_group
INSERT INTO tbl_group (access, created_at, description, name, type, created_by)
VALUES ('PUBLIC', '2025-03-14 15:20:00', 'Group description 1', 'Group 1', 'TypeA', 1),
       ('PRIVATE', '2025-03-14 15:25:00', 'Group description 2', 'Group 2', 'TypeB', 2);

-- 3. Chèn dữ liệu vào tbl_board
INSERT INTO tbl_board (created_at, description, name, created_by, group_id)
VALUES ('2025-03-14 15:30:00', 'Board description 1', 'Board 1', 1, 1);

-- 4. Chèn dữ liệu vào tbl_list
INSERT INTO tbl_list (name, priority, board_id, created_at, created_by)
VALUES ('List 1', 0, 1,'2025-03-14 15:30:00', 1),
       ('List 2', 1, 1,'2025-03-14 15:30:00', 1);

-- 5. Chèn dữ liệu vào tbl_card
INSERT INTO tbl_card (created_at, description, due_date, priority, title, created_by, list_id)
VALUES ('2025-03-14 15:40:00', 'Card description 1', '2025-03-20 12:00:00', 0, 'Card 1', 1, 1),
       ('2025-03-14 15:45:00', 'Card description 2', '2025-03-21 12:00:00', 1, 'Card 2', 2, 1),
       ('2025-03-14 15:50:00', 'Card description 3', '2025-03-22 12:00:00', 0, 'Card 3', 3, 2);

-- 6. Không chèn dữ liệu vào tbl_card_file

-- 7. Chèn dữ liệu vào tbl_label
INSERT INTO tbl_label (color, title, board_id)
VALUES ('BLUE', 'Blue Label', 1),
       ('RED', 'Red Label', 1),
       ('GREEN', 'Green Label', 1);

-- 8. Chèn dữ liệu vào tbl_card_label
INSERT INTO tbl_card_label (board_id, color, card_id)
VALUES (1, 'BLUE', 1),
       (1, 'RED', 1),
       (1, 'BLUE', 2),
       (1, 'GREEN', 2);

-- 9. Chèn dữ liệu vào tbl_group_member
INSERT INTO tbl_group_member (member_type, group_id, user_id)
VALUES ('MEMBER', 1, 2),
       ('OWNER', 1, 1);