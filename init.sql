CREATE TABLE IF NOT EXISTS books
(
    id           SERIAL NOT NULL,
    title        VARCHAR(100) NOT NULL,
    author       VARCHAR(100) NOT NULL,
    description  VARCHAR(1000) NOT NULL,
    page_count   INT          NOT NULL,
    publisher    VARCHAR(100) NOT NULL,
    publish_date VARCHAR(100) NOT NULL
);

INSERT INTO books(title, author, description, page_count, publisher, publish_date) VALUES
('Джейн Эйр','Шарлотта Бронте','Роман Шарлотты Бронте (1816 - 1855) "Джейн Эйр" принадлежит к лучшим произведениям английской литературы. Его можно отнести к жанру детектива - с тайной, которую героиня разгадывает на протяжении почти всего повествования, со сложной и тоже таинственной любовной историей. От большинства детективов это произведение отличает живая достоверность характеров и дымка романтизма.',528,'Проф-Издат','2011 г.'),
('Песнь Ахилла','Мадлен Миллер','"Песнь Ахилла" рассказывает о жизни Ахилла с детства и до смерти. Но нашими глазами становится не сам Ахилл, а его друг Патрокл. Мадлен Миллер не вносит принциальных изменений в классический сюжет. Она заполняет пустоты, придавая реальности жизни великого героя.',384,'Corpus','2020 г.'),
('Столпы Земли','Кена Фоллетта','Роман Кена Фоллетта – грандиозная панорама самых темных лет в истории Англии, когда борьба за престол и междоусобные войны были привычным фоном жизни. Автор разворачивает перед читателем сплетенные в единый клубок истории самых разных людей – от простых ремесленников до графов и членов королевских семей – и следит за своими героями на протяжении более чем полувека. Воссоздан целый мир страстей и преступлений, интриг, тайн, страхов, любви и верности, на фоне которого медленно и величественно разворачивается строительство самого высокого собора в Англии.',880,'AСТ','2015 г.');

CREATE TABLE IF NOT EXISTS favorites
(
    id           SERIAL NOT NULL,
    book_id      INT NOT NULL UNIQUE
);


CREATE SCHEMA "auth" AUTHORIZATION postgres;
GRANT ALL ON SCHEMA "auth" TO PUBLIC;
GRANT ALL ON SCHEMA "auth" TO postgres;

CREATE TABLE IF NOT EXISTS "auth".services
(
    name         VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO "auth".services(name)
	VALUES ('gateway'), ('book-service'), ('favorites-service');

CREATE TABLE IF NOT EXISTS "auth".permissions
(
	id           SERIAL NOT NULL,
    name_from    VARCHAR(50) NOT NULL,
	name_to      VARCHAR(50) NOT NULL
);

INSERT INTO "auth".permissions(name_from, name_to)
	VALUES ('gateway', 'book-service'),
			('gateway', 'favorites-service');