use javaee;

-- 创建商品表
CREATE TABLE IF NOT EXISTS `t_product` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `name` varchar(100) NOT NULL COMMENT '商品名称',
    `description` varchar(500) DEFAULT NULL COMMENT '商品描述',
    `price` decimal(10,2) NOT NULL COMMENT '商品价格',
    `stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
    `status` int(11) NOT NULL DEFAULT '1' COMMENT '商品状态（0：下架，1：上架）',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '删除标记（0：未删除，1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_status` (`status`),
    KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 添加10条测试数据
INSERT INTO `t_product` (`name`, `description`, `price`, `stock`, `status`) VALUES
    ('商品1', '测试商品1', 19.99, 100, 1),
    ('商品2', '测试商品2', 29.99, 50, 1),
    ('商品3', '测试商品3', 39.99, 20, 1),
    ('商品4', '测试商品4', 49.99, 10, 1),
    ('商品5', '测试商品5', 59.99, 5, 1);