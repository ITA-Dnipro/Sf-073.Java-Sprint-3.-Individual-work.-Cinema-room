CREATE TABLE users_tbl(
  u_id BIGINT AUTO_INCREMENT NOT NULL,
  u_name VARCHAR(50) NOT NULL,
  u_username VARCHAR(50) NOT NULL,
  u_password VARCHAR(100) NOT NULL,
  u_is_active BOOLEAN DEFAULT FALSE,
  u_roles ENUM('USER', 'ADMIN') ,
  PRIMARY KEY (u_id)
);