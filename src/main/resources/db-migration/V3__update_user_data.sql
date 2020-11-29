ALTER TABLE user
    ADD COLUMN email varchar(255) NOT NULL DEFAULT 'myemail',
    ADD COLUMN password varchar(255);

