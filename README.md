# <a name="up" /> Автоматизация тестов по скрипту для DB
## Скрипт для тестов по классу Экзотические продукты
### добавить продукты Овощ, Фрукт (с активным чекбоксом Экзотический)
```

/*Внести данные в таблицу*/
INSERT INTO food (H3 food_name, food_type, food_exotic )
VALUES (5, 'Пепино', 'Vegetable', 1 );

1. Посмотреть,что в таблице появились введенные данные
SELECT *
   FROM food;

-- 2. Проверить, что появился овощ с названием "Пепино"
SELECT food_name, food_type 
   FROM food
WHERE food_name = 'Пепино' ;

-- 3. Проверить, что введенный Овощ является Экзотическим, т.е. содержит значение true
SELECT * 
   FROM food
WHERE food_exotic is true;
 
 -- 4. Удалить данные для Овоща "Пепино" из таблицы
 DELETE 
   FROM food
 WHERE food_id = 5; 

 -- 5. Проверить,что созданные данные для "Пепино" удалились из таблицы.
 SELECT *
  FROM food;    <br>
      /* или так*/         <br>
 SELECT food_id, food_name, food_type, food_exotic<br>
FROM food; 

 -- 6. Проверить,что всего начальных позиций 4.
 SELECT
    COUNT (food_name) AS all_food
 FROM food;

------------------------------------------------

/*Внести данные в таблицу*/
INSERT INTO food (H3 food_name, food_type, food_exotic )
VALUES (5, 'Арбуз', 'Fruit', 1 );

-- 1. Посмотреть,что в таблице появились введенные данные
SELECT *
   FROM food;

-- 2. Проверить, что появился фрукт с названием "Арбуз"
SELECT food_name, food_type 
   FROM food
WHERE food_name = 'Арбуз' ;

-- 3. Проверить, что введенный Фрукт  является Экзотическим, т.е. содержит значение true
SELECT * 
   FROM food
WHERE food_exotic is true;
 
 -- 4. Удалить данные для Фрукт "Арбуз" из таблицы
 DELETE 
   FROM food
 WHERE food_id = 5; 

 -- 5. Проверить,что созданные данные для "Арбуз" удалились из таблицы.
  SELECT *
  FROM food;    <br>
      /* или так*/         <br>
 SELECT food_id, food_name, food_type, food_exotic<br>
FROM food; 

 -- 6. Проверить,что всего начальных позиций 4.
 SELECT
    COUNT (food_name) AS all_food
 FROM food; 
```
## Скрипт для тестов по классу не Экзотические продукты
### Добавить продукты Овощ, Фрукт (с не активным чекбоксом Экзотический)
```

/*Внести данные в таблицу*/
INSERT INTO food (H3 food_name, food_type, food_exotic )
VALUES (5, 'Морковь', 'Vegetable', 0 );

-- 1. Посмотреть,что в таблице появились введенные данные
SELECT *
   FROM food;

-- 2. Проверить, что появился овощ с названием "Морковь"
SELECT food_name, food_type 
   FROM food
WHERE food_name = 'Морковь' ;

-- 3. Проверить, что введенный Овощ не является Экзотическим, т.е. содержит значение false
SELECT * 
   FROM food
WHERE food_exotic is false;
 
 -- 4. Удалить данные для Овоща "Морковь" из таблицы
 DELETE 
   FROM food
 WHERE food_id = 5; 

 -- 5. Проверить,что созданные данные для "Морковь" удалились из таблицы.
 SELECT *
  FROM food;    <br>
      /* или так*/         <br>
 SELECT food_id, food_name, food_type, food_exotic<br>
FROM food; 

 -- 6. Проверить,что всего начальных позиций 4.
 SELECT
    COUNT (food_name) AS all_food
 FROM food; 

----------------------------------------
/*Внести данные в таблицу*/
INSERT INTO food (H3 food_name, food_type, food_exotic )
VALUES (5, 'Слива', 'Fruit', 0 );

-- 1. Посмотреть,что в таблице появились введенные данные
SELECT *
   FROM food;

-- 2. Проверить, что появился фрукт с названием "Слива"
SELECT food_name, food_type 
   FROM food
WHERE food_name = 'Слива' ;

-- 3. Проверить, что введенный Фрукт  не является Экзотическим, т.е. содержит значение false
SELECT * 
   FROM food
WHERE food_exotic is false;
 
 -- 4. Удалить данные для Фрукт "Слива" из таблицы
 DELETE 
   FROM food
 WHERE food_id = 5; 

 -- 5. Проверить,что созданные данные для "Слива" удалились из таблицы.
 SELECT *
  FROM food;    <br>
      /* или так*/         <br>
 SELECT food_id, food_name, food_type, food_exotic<br>
FROM food; 

 -- 6. Проверить,что всего начальных позиций 4.
 SELECT
    COUNT (food_name) AS all_food
 FROM food;
```
