DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_meal_seq RESTART WITH 5000000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, user_id) VALUES 
('Breakfast', 320 ,100000),
('Dinner', 550 ,100000),
('Midnight meal', 1200, 100001);

