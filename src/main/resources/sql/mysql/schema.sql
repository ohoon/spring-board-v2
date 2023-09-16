DROP DATABASE IF EXISTS board;

CREATE DATABASE board;
USE board;

CREATE TABLE member (
                        member_id          BIGINT AUTO_INCREMENT,
                        username           VARCHAR(20)  NOT NULL,
                        nickname           VARCHAR(12)  NOT NULL,
                        email              VARCHAR(128) NOT NULL,
                        is_quited          BOOLEAN      NOT NULL,
                        created_date       DATETIME     NOT NULL,
                        last_modified_date DATETIME     NOT NULL,
                        PRIMARY KEY (member_id)
) DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE auth_password (
                               auth_password_id   BIGINT,
                               member_id          BIGINT,
                               password           VARCHAR(255) NOT NULL,
                               created_date       DATETIME     NOT NULL,
                               last_modified_date DATETIME     NOT NULL,
                               PRIMARY KEY (auth_password_id),
                               FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE auth_social (
                             auth_social_id     BIGINT,
                             member_id          BIGINT,
                             subject            VARCHAR(255) NOT NULL,
                             provider           ENUM('GOOGLE', 'NAVER') NOT NULL,
                             created_date       DATETIME     NOT NULL,
                             last_modified_date DATETIME     NOT NULL,
                             PRIMARY KEY (auth_social_id),
                             FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE role (
                      role_id            BIGINT,
                      member_id          BIGINT,
                      type               ENUM('MEMBER', 'ADMIN') NOT NULL,
                      created_date       DATETIME  NOT NULL,
                      last_modified_date DATETIME  NOT NULL,
                      PRIMARY KEY (role_id),
                      FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE post (
                      post_id            BIGINT,
                      member_id          BIGINT,
                      title              VARCHAR(64) NOT NULL,
                      content            TEXT        NOT NULL,
                      author             VARCHAR(20) NOT NULL,
                      view               BIGINT      NOT NULL DEFAULT 0,
                      is_removed         BOOLEAN     NOT NULL,
                      created_date       DATETIME    NOT NULL,
                      last_modified_date DATETIME    NOT NULL,
                      PRIMARY KEY (post_id),
                      FOREIGN KEY (member_id) REFERENCES member (member_id)
) DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE comment (
                         comment_id         BIGINT,
                         member_id          BIGINT,
                         post_id            BIGINT,
                         parent_id          BIGINT,
                         content            VARCHAR(255) NOT NULL,
                         author             VARCHAR(20)  NOT NULL,
                         is_removed         BOOLEAN      NOT NULL,
                         created_date       DATETIME     NOT NULL,
                         last_modified_date DATETIME     NOT NULL,
                         PRIMARY KEY (comment_id),
                         FOREIGN KEY (member_id) REFERENCES member (member_id),
                         FOREIGN KEY (post_id)   REFERENCES post (post_id),
                         FOREIGN KEY (parent_id) REFERENCES comment (comment_id)
) DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE vote (
                      vote_id            BIGINT,
                      member_id          BIGINT,
                      post_id            BIGINT,
                      created_date       DATETIME     NOT NULL,
                      last_modified_date DATETIME     NOT NULL,
                      PRIMARY KEY (vote_id),
                      FOREIGN KEY (member_id) REFERENCES member (member_id),
                      FOREIGN KEY (post_id)   REFERENCES post (post_id)
);
