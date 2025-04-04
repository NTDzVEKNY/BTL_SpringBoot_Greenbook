-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
TRUNCATE TABLE categories;
TRUNCATE TABLE books;
TRUNCATE TABLE promotions;
TRUNCATE TABLE orders;
TRUNCATE TABLE order_details;
TRUNCATE TABLE blogs;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Users
INSERT INTO users (username, password, email, full_name, phone, address, role, is_active, created_at, updated_at)
VALUES
-- Admin password: admin123 (BCrypt encoded)
('admin', '$2a$10$rjf7sXQyN1HVAqO0k2A9xuS6oUSd8.DmIAJvQr9PJvwKdKdqMr4TC', 'admin@greenbook.com', 'System Admin', '0987654321', 'Hà Nội', 'ADMIN', 1, NOW(), NOW()),
-- Librarian password: lib123
('librarian', '$2a$10$tYWrm7KFj0Oaff0aLKsChOcK08GJqwXgV2W87qJ8VbXGejX5YZG4i', 'librarian@greenbook.com', 'Book Manager', '0912345678', 'Hồ Chí Minh', 'LIBRARIAN', 1, NOW(), NOW()),
-- Customer password: user123
('user1', '$2a$10$JHLAm8UzP.XjjDYy4Jjz5e50GsKliLzQ0iQp3nqZUwjUCJTG2vLES', 'user1@example.com', 'Nguyễn Văn A', '0901234567', 'Đà Nẵng', 'CUSTOMER', 1, NOW(), NOW()),
('user2', '$2a$10$JHLAm8UzP.XjjDYy4Jjz5e50GsKliLzQ0iQp3nqZUwjUCJTG2vLES', 'user2@example.com', 'Trần Thị B', '0909876543', 'Cần Thơ', 'CUSTOMER', 1, NOW(), NOW());

-- Insert Categories
INSERT INTO categories (name, description, is_active, created_at, updated_at)
VALUES
('Văn học Việt Nam', 'Các tác phẩm văn học Việt Nam nổi tiếng', 1, NOW(), NOW()),
('Văn học nước ngoài', 'Các tác phẩm văn học kinh điển và hiện đại trên thế giới', 1, NOW(), NOW()),
('Sách kinh tế', 'Sách về kinh tế, quản trị, tài chính', 1, NOW(), NOW()),
('Sách kỹ năng sống', 'Sách về kỹ năng sống, phát triển bản thân', 1, NOW(), NOW()),
('Sách thiếu nhi', 'Sách dành cho trẻ em và thiếu niên', 1, NOW(), NOW()),
('Sách học tập', 'Sách giáo khoa, tham khảo, luyện thi', 1, NOW(), NOW()),
('Sách IT & Công nghệ', 'Sách về công nghệ thông tin, lập trình', 1, NOW(), NOW());

-- Insert Books
INSERT INTO books (title, author, description, original_price, stock_quantity, sold_quantity, publisher, published_date, category_id, image_url, is_active, created_at, updated_at)
VALUES
('Dế Mèn Phiêu Lưu Ký', 'Tô Hoài', 'Tác phẩm văn học thiếu nhi nổi tiếng của nhà văn Tô Hoài kể về cuộc phiêu lưu của chú Dế Mèn.', 75000, 50, 20, 'NXB Kim Đồng', '2020-05-15', 5, '[]', 1, NOW(), NOW()),
('Truyện Kiều', 'Nguyễn Du', 'Tác phẩm kinh điển của văn học Việt Nam kể về cuộc đời của nàng Kiều.', 85000, 30, 15, 'NXB Văn Học', '2019-10-10', 1, '[]', 1, NOW(), NOW()),
('Nhà Giả Kim', 'Paulo Coelho', 'Câu chuyện về hành trình theo đuổi ước mơ của chàng chăn cừu Santiago.', 95000, 40, 35, 'NXB Hội Nhà Văn', '2020-02-20', 2, '[]', 1, NOW(), NOW()),
('Đắc Nhân Tâm', 'Dale Carnegie', 'Cuốn sách nổi tiếng về nghệ thuật ứng xử và giao tiếp.', 110000, 55, 45, 'NXB Tổng Hợp', '2021-03-15', 4, '[]', 1, NOW(), NOW()),
('Tiếng Anh 12', 'Bộ Giáo Dục', 'Sách giáo khoa Tiếng Anh lớp 12.', 45000, 100, 70, 'NXB Giáo Dục', '2022-07-10', 6, '[]', 1, NOW(), NOW()),
('Python cơ bản', 'Nguyễn Văn X', 'Giới thiệu về ngôn ngữ lập trình Python cho người mới bắt đầu.', 150000, 25, 10, 'NXB Bách Khoa', '2022-01-05', 7, '[]', 1, NOW(), NOW()),
('Phân tích chứng khoán', 'Benjamin Graham', 'Cẩm nang kinh điển về phân tích và đầu tư chứng khoán.', 180000, 20, 12, 'NXB Tài Chính', '2020-06-25', 3, '[]', 1, NOW(), NOW()),
('Doraemon', 'Fujiko F. Fujio', 'Bộ truyện tranh nổi tiếng về chú mèo máy đến từ tương lai.', 30000, 60, 40, 'NXB Kim Đồng', '2021-09-10', 5, '[]', 1, NOW(), NOW()),
('Không gia đình', 'Hector Malot', 'Câu chuyện xúc động về cậu bé Rémi mồ côi và hành trình tìm kiếm gia đình.', 95000, 15, 10, 'NXB Văn Học', '2020-11-15', 2, '[]', 1, NOW(), NOW()),
('Người giàu có nhất thành Babylon', 'George S. Clason', 'Khám phá bí quyết để đạt được thành công và thịnh vượng về tài chính.', 120000, 25, 20, 'NXB Lao Động', '2021-04-20', 3, '[]', 1, NOW(), NOW());

-- Insert Promotions
INSERT INTO promotions (name, code, type, value, start_date, end_date, description, is_active, created_at, updated_at)
VALUES
('Khuyến mãi mùa hè', 'SUMMER2023', 'PERCENTAGE', 15, '2023-06-01', '2023-08-31', 'Giảm 15% cho tất cả sách trong mùa hè', 1, NOW(), NOW()),
('Khuyến mãi đặc biệt', 'SPECIAL50K', 'FIXED_AMOUNT', 50000, '2023-05-01', '2023-12-31', 'Giảm 50,000đ cho đơn hàng từ 300,000đ', 1, NOW(), NOW()),
('Khuyến mãi sách IT', 'TECHBOOK', 'PERCENTAGE', 20, '2023-07-01', '2023-09-30', 'Giảm 20% cho sách IT & Công nghệ', 1, NOW(), NOW()),
('Khuyến mãi năm mới', 'NEWYEAR2024', 'PERCENTAGE', 25, '2023-12-15', '2024-01-15', 'Giảm 25% nhân dịp năm mới', 1, NOW(), NOW());

-- Insert Orders
INSERT INTO orders (user_id, total_amount, discount_amount, final_amount, status, shipping_address, payment_method, note, order_date, completed_date, created_at, updated_at)
VALUES
(3, 260000, 39000, 221000, 'COMPLETED', 'Đà Nẵng', 'CASH_ON_DELIVERY', 'Giao giờ hành chính', '2023-06-15 10:30:00', '2023-06-17 15:20:00', NOW(), NOW()),
(4, 150000, 30000, 120000, 'PENDING', 'Cần Thơ', 'BANK_TRANSFER', 'Gói quà tặng', '2023-07-20 14:45:00', NULL, NOW(), NOW()),
(3, 180000, 0, 180000, 'SHIPPED', 'Đà Nẵng', 'CREDIT_CARD', NULL, '2023-07-25 09:15:00', NULL, NOW(), NOW()),
(4, 265000, 50000, 215000, 'PROCESSING', 'Cần Thơ', 'CASH_ON_DELIVERY', 'Gọi trước khi giao', '2023-08-01 16:20:00', NULL, NOW(), NOW());

-- Insert Order Details
INSERT INTO order_details (order_id, book_id, quantity, original_price, discounted_price, promotion_id, created_at, updated_at)
VALUES
(1, 3, 1, 95000, 80750, 1, NOW(), NOW()),
(1, 4, 1, 110000, 93500, 1, NOW(), NOW()),
(1, 8, 2, 30000, 25500, 1, NOW(), NOW()),
(2, 7, 1, 180000, 150000, NULL, NOW(), NOW()),
(3, 6, 1, 150000, 150000, NULL, NOW(), NOW()),
(3, 10, 1, 120000, 120000, NULL, NOW(), NOW()),
(4, 4, 1, 110000, 110000, NULL, NOW(), NOW()),
(4, 9, 1, 95000, 95000, NULL, NOW(), NOW()),
(4, 2, 1, 85000, 85000, NULL, NOW(), NOW());

-- Insert Blogs
INSERT INTO blogs (title, content, image_url, user_id, status, published_date, created_at, updated_at)
VALUES
('Top 10 cuốn sách đáng đọc năm 2023', '<p>Năm 2023 đã chứng kiến sự ra đời của nhiều tác phẩm văn học xuất sắc. Dưới đây là 10 cuốn sách được đánh giá cao nhất:</p><ul><li>Cuốn sách 1</li><li>Cuốn sách 2</li><li>Cuốn sách 3</li><li>Cuốn sách 4</li><li>Cuốn sách 5</li><li>Cuốn sách 6</li><li>Cuốn sách 7</li><li>Cuốn sách 8</li><li>Cuốn sách 9</li><li>Cuốn sách 10</li></ul>', NULL, 1, 'PUBLISHED', '2023-06-20 10:00:00', NOW(), NOW()),
('Cách giúp trẻ em hình thành thói quen đọc sách', '<p>Đọc sách là một thói quen quan trọng cần được hình thành từ nhỏ. Bài viết này sẽ chia sẻ một số phương pháp giúp trẻ em yêu thích việc đọc sách từ sớm.</p><p>Nội dung chi tiết...</p>', NULL, 2, 'PUBLISHED', '2023-07-05 14:30:00', NOW(), NOW()),
('Xu hướng sách năm 2024', '<p>Dựa trên dữ liệu thị trường và phản hồi từ độc giả, chúng tôi dự đoán những xu hướng sách sẽ nổi bật trong năm 2024.</p><p>Nội dung chi tiết...</p>', NULL, 1, 'DRAFT', NULL, NOW(), NOW());