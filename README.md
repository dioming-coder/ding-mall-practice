# ding-seckill 仿电商秒杀系统

&gt; 14天魔鬼训练营实战项目 | 拒绝复制粘贴，手写理解每一行代码

## 🛠 技术栈

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 基础框架 | Spring Boot | 3.2.3 | JDK 17 + Maven |
| 数据访问 | MyBatis-Plus | 3.5.x | 简化 CRUD，专注业务 |
| 数据库 | MySQL | 9.5.0 | utf8mb4 字符集 |
| 缓存 | Redis | 7.x | 后续集成（Day 3-4） |
| 消息队列 | RabbitMQ | - | 后续集成（Day 5-6） |

## 📊 数据库设计（6表结构）

### 当前进度：Day 1 ✅
- [x] 项目搭建（Spring Boot 3.2.3 + Maven）
- [x] MySQL 环境配置（开机自启动 + IDEA 连接）
- [x] 数据库 `ding_seckill` 创建
- [x] **users 表设计与创建**（手写 SQL，工程级约束）
- [ ] product 表（待创建）
- [ ] seckill_product 表（待创建）
- [ ] order 表（待创建）
- [ ] order_item 表（待创建）
- [ ] stock_log 表（待创建）

### users 表结构（Day 1 成果）
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    role ENUM('USER','ADMIN') DEFAULT 'USER' COMMENT '角色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='用户表';