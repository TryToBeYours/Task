CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
);
CREATE TABLE tickets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject VARCHAR(255),
    description VARCHAR(2000),
    status VARCHAR(50),

    customer_id BIGINT,
    assigned_agent_id BIGINT,

    CONSTRAINT fk_ticket_customer
        FOREIGN KEY (customer_id) REFERENCES users(id),

    CONSTRAINT fk_ticket_agent
        FOREIGN KEY (assigned_agent_id) REFERENCES users(id)
);
