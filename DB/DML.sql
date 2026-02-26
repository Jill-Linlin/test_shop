USE shop_db;


INSERT INTO product (product_id, product_name, price, stock_quantity, image_url) VALUES 
('P001', 'osii 舒壓按摩椅', 98000.00, 5, 'P001.jpg'),
('P002', '網友最愛起司蛋糕', 1200.00, 50, 'P002.jpg'),
('P003', '真愛密碼項鍊', 8500.00, 20, 'P003.jpg');


INSERT INTO member (full_name, email) VALUES 
('王小明', 'xiaoming@example.com');


DELIMITER //
CREATE PROCEDURE sp_add_product(
    IN p_id VARCHAR(50),
    IN p_name VARCHAR(100),
    IN p_price DECIMAL(10,2),
    IN p_stock INT,
    IN p_image VARCHAR(255)
)
BEGIN
    INSERT INTO product (product_id, product_name, price, stock_quantity, image_url)
    VALUES (p_id, p_name, p_price, p_stock, p_image);
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_get_available_products()
BEGIN
    SELECT * FROM product WHERE stock_quantity > 0;
END //
DELIMITER ;