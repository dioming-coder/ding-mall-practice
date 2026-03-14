# ding-seckill 仿电商秒杀系统

&gt; 14天魔鬼训练营实战项目 | 拒绝复制粘贴，手写理解每一行代码

## 🛠 技术栈

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 基础框架 | Spring Boot | 3.2.3 | JDK 17 + Maven |
| 数据访问 | MyBatis-Plus | 3.5.5 | 简化 CRUD，专注业务 |
| 数据库 | MySQL | 9.5.0 | utf8mb4 字符集 |
| 缓存 | Redis | 7.x | 后续集成（Day 5-6） |
| 消息队列 | RabbitMQ | - | 后续集成（Day 7-8） |

## 📊 数据库设计（6表结构）

### 当前进度：Day 2 ✅✅（超额完成）

**已完成的表（手写 SQL 创建）：**

1. **users**（用户基础表）
    - 字段：id, username, password, phone, role, create_time, update_time
    - 设计亮点：使用 `users` 复数形式避免 MySQL 关键字冲突；`role` 使用 ENUM 约束；手机号/username 加 UNIQUE 唯一约束

2. **product**（商品基础表）
    - 字段：id, name, description, price, stock, status, create_time, update_time
    - 设计亮点：`price` 使用 `DECIMAL(10,2)` 精确存储金额（防浮点误差）；`status` 使用 TINYINT（1上架/0下架）而非 ENUM，预留扩展性

3. **seckill_product**（秒杀活动表）
    - 字段：id, product_id, seckill_price, stock_count, start_time, end_time, create_time
    - 设计亮点：秒杀库存与普通库存分离；时间窗口控制（start_time/end_time）防止提前或过期访问

4. **order**（订单主表）
    - 字段：id, order_no, user_id, total_amount, status, create_time, update_time
    - 设计亮点：业务单号 `order_no`（给用户看）与技术主键 `id`（给程序用）分离；`total_amount` 冗余存储（用空间换时间，避免查询时 JOIN 计算）

5. **order_item**（订单明细表）
    - 字段：id, order_id, product_id, product_name, product_price, quantity, total_price, create_time
    - 设计亮点：**数据快照**（`product_name` 和 `product_price` 在下单时冻结存储），防止后续商品改名/涨价导致历史订单显示错误

6. **stock_log**（库存操作日志表）
    - 字段：id, seckill_product_id, quantity, operation, order_no, create_time
    - 设计亮点：**审计表设计**（只插入不更新），记录每次库存变化（扣减/回滚/预扣）；`quantity` 使用正负数表示增减（负数扣减，正数回滚）

### 核心设计原则总结

- **反范式设计**：订单表冗余存储 `total_amount`，避免查询时实时计算，提高列表加载速度
- **数据快照**：订单明细冻结商品名称和价格，防止历史订单被后续商品信息变更篡改
- **逻辑外键**：不使用物理外键约束（影响并发性能），通过程序控制关联关系
- **精确计算**：金额统一使用 `DECIMAL(10,2)`，绝不使用 FLOAT/DOUBLE（0.1+0.2≠0.3）
- **状态扩展性**：状态字段使用 TINYINT 而非 ENUM，预留业务扩展空间（如新增"预售"、"退款中"等状态）

## 🚀 项目结构
ding-seckill/
├── src/main/java/com/dingyaoming.dingseckill/
│   ├── DingSeckillApplication.java    # 启动类（含@MapperScan）
│   ├── entity/                        # 实体层
│   │   └── User.java                  # 手写ORM映射（手动Getter/Setter）
│   ├── mapper/                        # 数据访问层
│   │   └── UserMapper.java            # 继承BaseMapper，理解动态代理
│   ├── controller/                    # 控制层
│   │   ├── HelloController.java       # Day1测试
│   │   └── UserController.java        # Day2用户增查接口（已打通数据库）
│   └── service/                       # 业务层（Day3填充）
├── src/main/resources/
│   └── application.yml                # MySQL连接配置
├── sql/
│   └── schema.sql                     # 6张表完整脚本（手写）
└── README.md                          # 本文件


## 📝 学习日志

### Day 1（2026-03-13）
- **上午**：解决 Spring Boot 404 问题（src目录必须与.idea同级）
- **下午**：MySQL 9.5.0 安装配置，手写 users 表 SQL
- **晚上**：理解 `BIGINT`、`ENUM`、`ON UPDATE` 等约束，建立工程规范意识

### Day 2（2026-03-14/15 深夜卷王模式）
- **上午**：集成 MyBatis-Plus，配置数据库连接
- **下午**：手写 User 实体类（手动 Getter/Setter，因 Lombok 环境问题深入理解封装原理）
- **晚上**：打通 Java ↔ MyBatis-Plus ↔ MySQL 全链路，成功实现用户插入和查询接口（`/user/add`、`/user/get/1`）
- **深夜（01:20）**：**手写完成剩余5张表**，深入理解数据库设计原则（外键关联、冗余存储、数据快照、审计表设计）

**心得**：从"能跑就行"到"工程规范"，从"复制粘贴"到"手写理解每一行SQL"，数据库设计能力质的飞跃。

## 🎯 后续计划

- **Day 3**：用户注册接口（接收前端参数）、BCrypt密码加密、参数校验（@Valid）、创建 Product 实体类
- **Day 4**：商品模块 CRUD、Redis 缓存预热
- **Day 5**：秒杀接口开发、库存扣减逻辑、超卖问题解决方案（乐观锁）
- **Day 6**：订单模块、事务管理、库存回滚机制
- **Day 7-8**：RabbitMQ 异步处理、秒杀接口限流防刷
- **Day 9-10**：JDBC 原理深入、手写简化版 MyBatis（理解框架底层）
- **Day 11-14**：压测优化、集群部署、项目收尾

## ⚠️ 踩坑记录

1. **404 问题**：Spring Boot 启动正常但接口 404，原因是 src 被错误创建在 .idea 目录下。**解决**：移动 src 到项目根目录。
2. **Lombok 环境冲突**：JDK 17 与 Lombok 版本不兼容导致编译失败。**解决**：改为手动编写 Getter/Setter，反而加深对 JavaBean 规范的理解。
3. **MySQL 驱动问题**：`com.mysql.cj.jdbc.Driver` 报错。**解决**：pom.xml 中移除 `<scope>runtime</scope>`，让编译期也能找到类。
4. **Git 合并冲突**：远程仓库已有 README 导致 push 失败。**解决**：`git pull origin main --allow-unrelated-histories` 合并后再 push。
5. **数据库设计原则**：
    - 不用物理外键（影响并发），用逻辑外键（程序控制）
    - 金钱用 DECIMAL(10,2)，绝不用 FLOAT/DOUBLE
    - 状态字段用 TINYINT 不用 ENUM（预留扩展性）
    - 订单数据必须快照（防止商品信息变更篡改历史）

---
*项目正在高强度开发中，预计 14 天完成核心功能。当前进度：Day 2 超额完成，6张表设计完毕，准备进入业务逻辑开发阶段。*