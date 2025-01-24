INSERT INTO users (full_name, email, password, created_at, updated_at) VALUES
('Deepak', 'deepak.manoj@hotmail.com', '$2a$10$a80vedL/LhhESAyO1TBwzOdLulrq0GBLluUmt78bEy798kYATrjhW', '2025-01-25 02:06:51.458000', '2025-01-25 02:06:51.458000'),
('Author Two', 'author@hotmail.com', '$2a$10$tu7k/DNMBhyXEcwpTOyRyOUwOJw1INhtiznw333Yzd9VO77UilEhK', '2025-01-25 02:07:31.840000', '2025-01-25 02:07:31.840000');

INSERT INTO articles (title, content, created_at, updated_at, users_id, is_active) VALUES
('First Article', 'This is my first article!', '2025-01-25 02:07:07.094000', '2025-01-25 02:07:07.094000', 1, 'true'),
('Second Article', 'This is my second article!', '2025-01-25 02:07:15.164000', '2025-01-25 02:07:15.164000', 1, 'true'),
('This is my first article as Author', 'This is my content section!', '2025-01-25 02:07:51.284000', '2025-01-25 02:07:51.284000', 2, 'true');