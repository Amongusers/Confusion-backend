ALTER TABLE authority ALTER COLUMN aut_id SET DEFAULT nextval('aut_id_sequence');
ALTER TABLE app_user ALTER COLUMN user_id SET DEFAULT nextval('user_id_sequence');
ALTER TABLE role ALTER COLUMN role_id SET DEFAULT nextval('role_id_sequence');
ALTER TABLE game_statistic ALTER COLUMN gs_id SET DEFAULT nextval('gs_id_sequence');
ALTER TABLE user_statistic ALTER COLUMN us_id SET DEFAULT nextval('us_id_sequence');
ALTER TABLE task ALTER COLUMN task_id SET DEFAULT nextval('task_id_sequence');
ALTER TABLE task_in_room ALTER COLUMN tir_id SET DEFAULT nextval('tir_id_sequence');
ALTER TABLE room ALTER COLUMN room_id SET DEFAULT nextval('room_id_sequence');
ALTER TABLE voting ALTER COLUMN vt_id SET DEFAULT nextval('vt_id_sequence');
--Добавление ролей
INSERT INTO authority (aut_authority) VALUES ('ROLE_USER');
INSERT INTO authority (aut_authority) VALUES ('ROLE_ADMIN');

