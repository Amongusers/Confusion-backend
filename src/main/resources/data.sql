ALTER TABLE authority ALTER COLUMN aut_id SET DEFAULT nextval('aut_id_sequence');
ALTER TABLE app_user ALTER COLUMN user_id SET DEFAULT nextval('user_id_sequence');
--Добавление ролей
INSERT INTO authority (aut_authority) VALUES ('ROLE_USER');
INSERT INTO authority (aut_authority) VALUES ('ROLE_ADMIN');

--Добавление админа username:admin password:admin
INSERT INTO app_user (user_username, user_password, user_email, created_user)
VALUES ('admin', '$2a$10$eObqG4zk7CbKPPTv0daHm.bcpK6zwyFPpjAVXOeDWrT/3TpVcxpia', 'admin@ya.ru', current_date);

--Добавление связей
INSERT INTO app_user_authority (user_id, aut_id) VALUES (1, 1);