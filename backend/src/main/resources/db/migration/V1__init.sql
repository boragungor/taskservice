CREATE TABLE tasks (
                       id          BIGSERIAL PRIMARY KEY,
                       user_id     BIGINT       NOT NULL,
                       title       VARCHAR(120) NOT NULL,
                       description VARCHAR(1000),
                       status      VARCHAR(20)  NOT NULL,
                       due_date    DATE,
                       version     BIGINT       NOT NULL,
                       CONSTRAINT uk_tasks_user_title UNIQUE (user_id, title)
);