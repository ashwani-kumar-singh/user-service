CREATE TABLE users_roles (
  id int NOT NULL AUTO_INCREMENT,
  user_id int  NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);