-- 校园二手商品交易系统数据库脚本
-- 合并了原始的表创建脚本和数据更新脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS campus_market CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE campus_market;

-- 创建商品表
CREATE TABLE IF NOT EXISTS goods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    category VARCHAR(50) NOT NULL COMMENT '商品分类',
    condition_status VARCHAR(20) NOT NULL COMMENT '商品成色',
    image_url VARCHAR(500) COMMENT '商品图片URL',
    trade_time VARCHAR(50) NOT NULL COMMENT '交易时间',
    trade_location VARCHAR(100) NOT NULL COMMENT '交易地点',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    seller_id BIGINT NOT NULL COMMENT '卖家ID',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '商品状态：ACTIVE-上架，INACTIVE-下架，DELETED-删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 插入测试数据
INSERT INTO goods (name, description, price, category, condition_status, image_url, trade_time, trade_location, contact_phone, seller_id, status) VALUES
('iPhone 13 Pro', '苹果手机，256GB，深空灰色', 5999.00, '电子产品', '九成新', 'https://example.com/iphone13.jpg', '工作日晚上7-9点', '学校图书馆门口', '13812345678', 1, 'ACTIVE'),
('MacBook Pro 2021', '苹果笔记本电脑，16GB内存，512GB存储', 12999.00, '电子产品', '八成新', 'https://example.com/macbook.jpg', '周末全天', '学生宿舍楼下', '13987654321', 1, 'ACTIVE'),
('Nike Air Max', '耐克运动鞋，42码，白色', 899.00, '服装鞋帽', '七成新', 'https://example.com/nike.jpg', '下午2-6点', '体育馆门口', '13765432198', 2, 'ACTIVE'),
('高等数学教材', '同济版高等数学第七版，无笔记', 45.00, '图书文具', '八成新', 'https://example.com/math.jpg', '课间时间', '教学楼大厅', '13654321087', 3, 'ACTIVE'),
('保温杯', '不锈钢保温杯，500ml容量', 68.00, '生活用品', '全新', 'https://example.com/cup.jpg', '中午12-1点', '食堂门口', '13543210976', 4, 'ACTIVE'),
('篮球', '斯伯丁篮球，标准7号球', 158.00, '体育用品', '九成新', 'https://example.com/basketball.jpg', '晚上6-8点', '篮球场', '13432109865', 5, 'ACTIVE');

-- 创建交易表
CREATE TABLE IF NOT EXISTS trade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '交易ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_title VARCHAR(100) NOT NULL COMMENT '商品标题',
    product_price DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    product_image VARCHAR(500) COMMENT '商品图片',
    buyer_id BIGINT NOT NULL COMMENT '买家ID',
    seller_id BIGINT NOT NULL COMMENT '卖家ID',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '交易状态：PENDING-待处理，ACCEPTED-已接受，SHIPPED-已发货，COMPLETED-已完成，CANCELLED-已取消',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    quantity INT DEFAULT 1 COMMENT '数量',
    shipping_address VARCHAR(200) COMMENT '收货地址',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易表';

-- 创建评价表
CREATE TABLE IF NOT EXISTS review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评价ID',
    order_id BIGINT NOT NULL COMMENT '订单ID（交易ID）',
    reviewer_id BIGINT NOT NULL COMMENT '评论者ID',
    reviewee_id BIGINT NOT NULL COMMENT '被评论者ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    rating INT NOT NULL COMMENT '评分（1-5分）',
    content TEXT COMMENT '评价内容',
    anonymity BOOLEAN DEFAULT FALSE COMMENT '是否匿名，0-不匿名，1-匿名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_id (order_id),
    INDEX idx_reviewer_id (reviewer_id),
    INDEX idx_reviewee_id (reviewee_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评价表';

-- 创建举报表
CREATE TABLE IF NOT EXISTS report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '举报ID',
    reporter_id BIGINT NOT NULL COMMENT '举报人ID',
    reported_id BIGINT NOT NULL COMMENT '被举报对象ID',
    report_type VARCHAR(20) NOT NULL COMMENT '举报类型：GOODS-商品，USER-用户，REVIEW-评价',
    target_id BIGINT NOT NULL COMMENT '目标ID（商品ID、用户ID或评价ID）',
    reason VARCHAR(200) NOT NULL COMMENT '举报原因',
    description TEXT COMMENT '详细描述',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '处理状态：PENDING-待处理，PROCESSING-处理中，RESOLVED-已解决，REJECTED-已拒绝',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_reporter_id (reporter_id),
    INDEX idx_reported_id (reported_id),
    INDEX idx_report_type (report_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='举报表';

-- 查看表结构
DESCRIBE goods;

-- 查看插入的数据
SELECT id, name, trade_time, trade_location, contact_phone FROM goods;
