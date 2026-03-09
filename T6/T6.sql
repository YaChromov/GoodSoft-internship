

-- 1.1 Создание таблицы пользователей (users) / добавил условие, чтобы при наличии таблицы действие пропускалось
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NULL
);

-- 1.2 Создание таблицы ролей (roles)
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);


-- 2.1 Добавление колонки "возраст" с проверкой, что значение > 18
ALTER TABLE authorization.users
ADD COLUMN age INTEGER NOT NULL,
ADD CONSTRAINT check_age_over_18 CHECK (age > 18);

-- 2.2 Добавление колонки "зарплата" (может быть пустой)
ALTER TABLE users
ADD COLUMN salary NUMERIC(10, 2) NULL;

-- 2.3 Создание индекса для ускорения поиска по имени пользователя
CREATE INDEX idx_users_name ON users(name);

-- 2.4 Добавление колонки для связи с ролями и создание внешнего ключа
ALTER TABLE users
ADD COLUMN role_id INTEGER,
ADD CONSTRAINT fk_users_role
    FOREIGN KEY (role_id)
    REFERENCES authorization.roles(id)
    ON DELETE SET NULL; -- Если роль удалена, у пользователя поле станет NULL



-- 3.1 Заполнение таблицы ролей
INSERT INTO roles (name) VALUES
    ('Администратор'),
    ('Менеджер'),
    ('Разработчик'),
    ('Бухгалтер'),
    ('Гость');


-- 3.2 Заполнение таблицы пользователей
INSERT INTO users (login, password, name, birth_date, age, salary, role_id) VALUES
    -- Администраторы
    ('admin.ivan', 'hash_ivan123', 'Иван Петров', '1985-03-15', 38, 150000.00, 1),
    ('admin.anna', 'hash_anna456', 'Анна Смирнова', '1990-07-22', 33, 145000.00, 1),

    -- Менеджеры
    ('manager.sergey', 'hash_sergey789', 'Сергей Козлов', '1988-11-03', 35, 95000.00, 2),
    ('manager.elena', 'hash_elena012', 'Елена Новикова', '1992-04-18', 31, 92000.00, 2),
    ('manager.dmitry', 'hash_dmitry345', 'Дмитрий Волков', '1987-09-25', 36, 98000.00, 2),
    ('manager.olga', 'hash_olga678', 'Ольга Морозова', '1991-12-07', 32, 93000.00, 2),
    ('manager.alexandr', 'hash_alex901', 'Александр Павлов', '1989-06-13', 34, 94000.00, 2),

    -- Разработчики
    ('dev.alexey', 'hash_alexey234', 'Алексей Соколов', '1995-06-14', 28, 120000.00, 3),
    ('dev.maria', 'hash_maria567', 'Мария Лебедева', '1993-08-30', 30, 125000.00, 3),
    ('dev.pavel', 'hash_pavel890', 'Павел Федоров', '1994-02-21', 29, 118000.00, 3),
    ('dev.ekaterina', 'hash_ekater123', 'Екатерина Григорьева', '1996-10-05', 27, 115000.00, 3),

    -- Бухгалтеры
    ('buh.victor', 'hash_victor456', 'Виктор Степанов', '1978-05-12', 45, 88000.00, 4),
    ('buh.nadezhda', 'hash_nadezhda78', 'Надежда Орлова', '1982-09-19', 41, 86000.00, 4),
    ('buh.tatyana', 'hash_tatyana90', 'Татьяна Михайлова', '1980-03-25', 43, 89000.00, 4),

    -- Гости
    ('guest.andrey', 'hash_andrey12', 'Андрей Николаев', NULL, 22, NULL, 5),
    ('guest.tatiana', 'hash_tatia34', 'Татьяна Семенова', '2000-03-08', 24, 35000.00, 5),
    ('guest.maxim', 'hash_maxim56', 'Максим Зайцев', NULL, 20, NULL, 5),
    ('guest.irina', 'hash_irina78', 'Ирина Васильева', '1999-07-16', 23, NULL, 5),
    ('guest.kirill', 'hash_kirill90', 'Кирилл Белов', NULL, 19, 32000.00, 5),
    ('guest.alena', 'hash_alena11', 'Алена Сорокина', '2001-12-03', 21, NULL, 5);


-- 3.3 Проверка заполненных данных
-- Посмотреть все роли

SELECT * FROM roles ORDER BY id;

-- Посмотреть всех пользователей с названиями ролей
SELECT
    u.id,
    u.login,
    u.name AS "ФИО",
    u.age AS "Возраст",
    u.birth_date AS "Дата рождения",
    u.salary AS "Зарплата (руб)",
    r.name AS "Роль"
FROM users u
LEFT JOIN roles r ON u.role_id = r.id
ORDER BY r.id, u.age;

-- =============================================
-- 3.4. Аналитические запросы
-- =============================================

SELECT
    r.name AS "Роль",
    COUNT(u.id) AS "Количество",
    MIN(u.age) AS "Мин. возраст",
    MAX(u.age) AS "Макс. возраст",
    ROUND(AVG(u.age)) AS "Ср. возраст",
    ROUND(AVG(u.salary)) AS "Ср. зарплата"
FROM roles r
LEFT JOIN users u ON u.role_id = r.id
GROUP BY r.id, r.name
ORDER BY r.id;

-- Сотрудники с самой высокой зарплатой
SELECT name, age, salary,
    (SELECT name FROM roles WHERE id = u.role_id) AS role
FROM users u
WHERE salary IS NOT NULL
ORDER BY salary DESC
LIMIT 5;

-- Самые молодые сотрудники
SELECT name, age,
    (SELECT name FROM roles WHERE id = u.role_id) AS role
FROM users u
ORDER BY age
LIMIT 5;

-- Сотрудники у которых день рождения весной (март-май)
SELECT name, birth_date, age,
    TO_CHAR(birth_date, 'Month') AS month
FROM users
WHERE EXTRACT(MONTH FROM birth_date) IN (3, 4, 5)
ORDER BY EXTRACT(MONTH FROM birth_date);

-- 4.1 Выбрать всех пользователей (все колонки)
SELECT * FROM users;

-- 4.2 Выбрать всех пользователей с заполненной датой рождения

SELECT * FROM users
WHERE birth_date IS NOT NULL;

-- 4.3 Выбрать всех пользователей с датой рождения в заданных пределах
-- (например, с 1990 по 1995 год)

SELECT * FROM users
WHERE birth_date BETWEEN '1990-01-01' AND '1995-12-31'
ORDER BY birth_date;

-- 4.4 Выбрать количество пользователей с одинаковым возрастом
-- (на выходе 2 колонки - возраст и количество)

SELECT
    age AS "Возраст",
    COUNT(*) AS "Количество"
FROM users
GROUP BY age
ORDER BY age;


-- 4.5 К предыдущему добавить ограничение по количеству только больше 1
-- и отсортировать по количеству по убыванию

SELECT
    age AS "Возраст",
    COUNT(*) AS "Количество"
FROM users
GROUP BY age
HAVING COUNT(*) > 1
ORDER BY COUNT(*) DESC;

-- 4.6 Выбрать только пользователей с ролями (роли так же вывести)
-- (INNER JOIN - только пользователи у которых есть роль)

SELECT
    u.*,
    r.name AS role_name
FROM users u
INNER JOIN roles r ON u.role_id = r.id;


-- 4.7 Выбрать всех пользователей и их роли
-- (LEFT JOIN - все пользователи, даже если роль не назначена)

SELECT
    u.*,
    r.name AS role_name
FROM users u
LEFT JOIN roles r ON u.role_id = r.id;

-- 4.8 Ограничить вывод предыдущего запроса 5-ю записями
-- (сначала просто 5 записей)

SELECT
    u.*,
    r.name AS role_name
FROM users u
LEFT JOIN roles r ON u.role_id = r.id
LIMIT 5;


-- 4.9 5 записей, начиная с 3-й (пропустить 2, взять 5)

SELECT
    u.*,
    r.name AS role_name
FROM users u
LEFT JOIN roles r ON u.role_id = r.id
OFFSET 2  -- пропускаем первые 2 записи
LIMIT 5;  -- берем следующие 5 записей

-- Дополнительные полезные вариации

-- Вариант запроса 3 с параметрами (для использования в приложении)

SELECT * FROM users
WHERE birth_date BETWEEN '1985-01-01' AND '1990-12-31'
ORDER BY birth_date;

-- Вариант запроса 6 с более детальной информацией о роли
SELECT
    u.id,
    u.name AS user_name,
    u.age,
    u.salary,
    r.id AS role_id,
    r.name AS role_name
FROM users u
INNER JOIN roles r ON u.role_id = r.id;

-- Вариант запроса 8 с сортировкой перед ограничением
SELECT
    u.*,
    r.name AS role_name
FROM users u
LEFT JOIN roles r ON u.role_id = r.id
ORDER BY u.age DESC  -- сначала сортируем
LIMIT 5;             -- потом ограничиваем


-- =====================================================================

-- 5.1 Изменить колонку зарплата на NOT NULL со значением по умолчанию 1000


-- Текущие значения
SELECT id, name, age, salary FROM users ORDER BY salary NULLS LAST;

-- Установка значения по умолчанию для новых записей
ALTER TABLE users
ALTER COLUMN salary SET DEFAULT 1000;

-- Обновление существующих NULL на значение по умолчанию
UPDATE users
SET salary = 1000
WHERE salary IS NULL;

-- Проверка, что NULL-значений больше нет
SELECT id, name, age, salary FROM users WHERE salary IS NULL;

-- Установка NOT NULL
ALTER TABLE users
ALTER COLUMN salary SET NOT NULL;

-- Результат
SELECT id, name, age, salary FROM users ORDER BY age;


-- 5.2 Посчитать среднюю зарплату пользователей с возрастом < 25 и > 25
--    (используя объединение запросов)

-- Использование объединение запросов
SELECT
    'Возраст < 25' AS "Категория",
    ROUND(AVG(salary), 2) AS "Средняя зарплата",
    COUNT(*) AS "Количество"
FROM users
WHERE age < 25

UNION ALL

SELECT
    'Возраст > 25' AS "Категория",
    ROUND(AVG(salary), 2) AS "Средняя зарплата",
    COUNT(*) AS "Количество"
FROM users
WHERE age > 25

UNION ALL

SELECT
    'Возраст = 25' AS "Категория",
    ROUND(AVG(salary), 2) AS "Средняя зарплата",
    COUNT(*) AS "Количество"
FROM users
WHERE age = 25;

--=================================================
-- 6.1 Всем у кого зарплата меньше 3000 добавить к зарплате 20%


-- Текущие данные
SELECT id, name, age, salary
FROM users
WHERE salary < 3000
ORDER BY salary;

-- Обновление
UPDATE users
SET salary = salary * 1.2  -- увеличиваю на 20%
WHERE salary < 3000;

-- результат
SELECT id, name, age, salary
FROM users
WHERE salary < 3000;

-- 6.2 Тем у кого имя начинается на заданную букву
--    роль установить на "Менеджер"


UPDATE users
SET role_id = 2  -- ID роли "Менеджер"
WHERE name LIKE 'А%'  -- имена на букву 'А'
   OR name LIKE 'а%';  -- для строчных

-- 2. Проверяем пользователей с обновленной ролью (для буквы 'А')
SELECT
    u.id,
    u.name AS "ФИО",
    u.age AS "Возраст",
    r.name AS "Новая роль"
FROM users u
LEFT JOIN roles r ON u.role_id = r.id
WHERE u.name LIKE 'А%'
   OR u.name LIKE 'а%'
ORDER BY u.name;


--====================================================================================


-- 7 Новая таблица для связи пользователей и ролей (many-to-many)

CREATE TABLE IF NOT EXISTS user_roles (
    id SERIAL PRIMARY KEY,  первичный ключ
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- когда назначена роль
    assigned_by VARCHAR(100) DEFAULT 'system',  -- кто назначил
    UNIQUE(user_id, role_id),  -- чтобы не было дубликатов (составной уникальный ключ)
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- индексы для быстрого поиска
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);

-- Переносим существующие связи из таблицы users

-- Переносим все существующие роли пользователей в новую таблицу
INSERT INTO user_roles (user_id, role_id, assigned_by)
SELECT
    u.id,
    u.role_id,
    'migration'  -- помечаем, что это перенос с предыдущей структуры
FROM users u
WHERE u.role_id IS NOT NULL;  -- только те, у кого есть роль

-- Проверяем, сколько записей перенеслось
SELECT COUNT(*) AS "Перенесено связей" FROM user_roles;

-- Удаление старой колонки role_id из таблицы users


-- Сначала удаляем внешний ключ (если он еще есть)
ALTER TABLE users DROP CONSTRAINT IF EXISTS fk_users_role;

-- Затем удаляем колонку role_id
ALTER TABLE users DROP COLUMN IF EXISTS role_id;

-- 4. Добавляем новые роли пользователям

-- Администраторам добавил роль "Менеджер"
INSERT INTO user_roles (user_id, role_id, assigned_by)
SELECT
    u.id,
    r.id,
    'admin'
FROM users u
CROSS JOIN roles r
WHERE u.name LIKE 'Иван%'  -- Иванов Иван
  AND r.name = 'Менеджер'
  AND NOT EXISTS (  -- проверяем, что такой связи еще нет
      SELECT 1 FROM user_roles ur
      WHERE ur.user_id = u.id AND ur.role_id = r.id
  );

-- Разработчикам добавил роль "Гость"
INSERT INTO user_roles (user_id, role_id, assigned_by)
SELECT
    u.id,
    r.id,
    'admin'
FROM users u
CROSS JOIN roles r
WHERE u.name LIKE '%Соколов%'  -- Алексей Соколов
  AND r.name = 'Гость'
  AND NOT EXISTS (
      SELECT 1 FROM user_roles ur
      WHERE ur.user_id = u.id AND ur.role_id = r.id
  );


-- Структура

-- Все пользователи и их роли (теперь их может быть несколько)
SELECT
    u.id,
    u.name AS "Пользователь",
    u.age AS "Возраст",
    STRING_AGG(r.name, ', ' ORDER BY r.name) AS "Роли",  -- собираем все роли в строку
    COUNT(r.id) AS "Количество ролей"
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
GROUP BY u.id, u.name, u.age
ORDER BY u.name;

-- Пользователи у которых больше одной роли
SELECT
    u.name AS "Пользователь",
    COUNT(ur.role_id) AS "Количество ролей",
    STRING_AGG(r.name, ', ') AS "Список ролей"
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
GROUP BY u.id, u.name
HAVING COUNT(ur.role_id) > 1
ORDER BY COUNT(ur.role_id) DESC;

-- Все роли и сколько пользователей их имеют
SELECT
    r.name AS "Роль",
    COUNT(ur.user_id) AS "Количество пользователей",
    STRING_AGG(u.name, ', ') AS "Пользователи"
FROM roles r
LEFT JOIN user_roles ur ON r.id = ur.role_id
LEFT JOIN users u ON ur.user_id = u.id
GROUP BY r.id, r.name
ORDER BY COUNT(ur.user_id) DESC;

-- Детальная информация по конкретному пользователю
SELECT
    u.name AS "Пользователь",
    r.name AS "Роль",
    ur.assigned_at AS "Назначена",
    ur.assigned_by AS "Кем назначена"
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.name LIKE 'Иван%'
ORDER BY r.name;

-- 8.1 Назначить заданному пользователю 3 роли, которые у него отсутствуют


-- Выбор пользователя (например, с именем 'Иван Петров')
SELECT id, name FROM users WHERE name = 'Иван Петров';

-- Его роли
SELECT
    u.name AS "Пользователь",
    r.name AS "Существующие роли"
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.name = 'Иван Петров';

-- Назначаю 3 отсутствующие роли пользователю с id = 1
INSERT INTO user_roles (user_id, role_id, assigned_by)
SELECT 1, id, 'admin'
FROM roles
WHERE name IN ('Разработчик', 'Бухгалтер', 'Гость')
  AND id NOT IN (  -- которых еще нет у пользователя
      SELECT role_id FROM user_roles WHERE user_id = 1
  );

-- Результат
SELECT
    u.name AS "Пользователь",
    STRING_AGG(r.name, ', ' ORDER BY r.name) AS "Все роли"
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE u.id = 1
GROUP BY u.id, u.name;


-- 8.2 Выбрать всех пользователей и их роли


-- Все пользователи (включая тех, у кого нет ролей)
SELECT
    u.id,
    u.name AS "Пользователь",
    u.age AS "Возраст",
    u.salary AS "Зарплата",
    COALESCE(STRING_AGG(r.name, ', ' ORDER BY r.name), 'нет ролей') AS "Роли"
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
GROUP BY u.id, u.name, u.age, u.salary
ORDER BY u.name;



-- 8.3 Выбрать только пользователей с ролями

-- Список пользователей, у которых есть хотя бы одна роль
SELECT DISTINCT
    u.id,
    u.name AS "Пользователь с ролями",
    COUNT(ur.role_id) OVER (PARTITION BY u.id) AS "Количество ролей"
FROM users u
INNER JOIN user_roles ur ON u.id = ur.user_id
ORDER BY u.name;



-- 8.4 Удалить выбранного пользователя (у которого есть роли)

-- Выбираю пользователя для удаления (например, с id = 1)
SELECT
    u.id,
    u.name AS "Пользователь для удаления",
    COUNT(ur.role_id) AS "Количество ролей",
    STRING_AGG(r.name, ', ') AS "Роли"
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
WHERE u.id = 1
GROUP BY u.id, u.name;

-- Проверка ролей
SELECT
    CASE
        WHEN EXISTS (SELECT 1 FROM user_roles WHERE user_id = 1)
        THEN 'У пользователя есть роли'
        ELSE 'У пользователя нет ролей'
    END AS "Статус";

-- Удаляем пользователя
-- Так как у нас ON DELETE CASCADE в внешнем ключе, роли удалятся автоматически
DELETE FROM users WHERE id = 1;

-- Проверка удаления пользователя
SELECT * FROM users WHERE id = 1;

-- Проверка удаленных ролей из связующей таблицы
SELECT * FROM user_roles WHERE user_id = 1;


-- 9.1 Выбрать всех пользователей и все роли независимо от привязок одним запросом

-- CROSS JOIN
SELECT
    u.id AS user_id,
    u.name AS "Пользователь",
    u.age AS "Возраст",
    r.id AS role_id,
    r.name AS "Роль"
FROM users u
CROSS JOIN roles r
ORDER BY u.name, r.name;



-- 9.2 Выбрать всех пользователей, у которых нет привязок к ролям
--    (пользователи без ролей)


-- Пользователи, у которых нет ни одной роли
SELECT
    u.id,
    u.name AS "Пользователь без ролей",
    u.age,
    u.salary
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
WHERE ur.user_id IS NULL  -- нет записей в user_roles
ORDER BY u.name;



-- 9.3 Выбрать все роли, у которых нет привязок к пользователям
--    (роли без пользователей)


-- Роли, которые не назначены ни одному пользователю
SELECT
    r.id,
    r.name AS "Роль без пользователей"
FROM roles r
LEFT JOIN user_roles ur ON r.id = ur.role_id
WHERE ur.role_id IS NULL  -- нет записей в user_roles
ORDER BY r.id;

