Create Table users(
id int NOT NULL AUTO_INCREMENT,
first_name varchar(255) NOT NULL,
last_name varchar(512) NOT NULL,
contact varchar(50) NOT NULL,
email varchar(50) NOT NULL,
address varchar (512) NOT NULL,
created_by varchar(20) NOT NULL,
updated_by varchar (20),
created_at TIMESTAMP DEFAULT NOW(),
update_at TIMESTAMP,
PRIMARY KEY (id)
);