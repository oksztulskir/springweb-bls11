 ALTER TABLE user
     ADD CONSTRAINT email_uc UNIQUE (email);
 ALTER TABLE user
     ADD CONSTRAINT login_uc UNIQUE (login);
