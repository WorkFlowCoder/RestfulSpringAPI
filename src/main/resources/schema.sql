DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description CLOB NOT NULL,
    image VARCHAR(255),
    category VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INTEGER NOT NULL,
    rating INTEGER NOT NULL,
    code VARCHAR(255),
    shellId INT,
    internalReference VARCHAR(255),
    inventoryStatus VARCHAR(255),
    
    createdAt BIGINT,
    updatedAt BIGINT
);
