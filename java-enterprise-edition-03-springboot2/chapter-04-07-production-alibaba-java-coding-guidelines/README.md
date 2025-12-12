# 商品管理系统示例

这是一个符合《阿里巴巴Java开发手册》和RESTful最佳实践的商品管理模块示例项目。

## 项目结构

```
src/main/java/org/bluebridge/
├── controller/           # 控制器层
├── dto/                  # 数据传输对象
├── entity/               # 实体类
├── exception/            # 异常处理
├── service/              # 服务层
│   └── impl/             # 服务实现
└── vo/                   # 视图对象
```

## 功能特性

1. 完整的RESTful API设计
2. 统一响应体封装
3. 参数校验（JSR-380）
4. 全局异常处理
5. CRUD操作完整实现
6. 分页查询支持
7. 批量操作支持
8. 逻辑删除支持

## 技术栈

- Spring Boot 2.7.0
- Java 8
- Lombok
- Maven

## API接口

| 接口 | 方法 | URL | 描述 |
|------|------|-----|------|
| 创建商品 | POST | /api/products | 创建新商品 |
| 批量创建商品 | POST | /api/products/batch | 批量创建商品 |
| 全量更新商品 | PUT | /api/products/{id} | 全量更新指定ID的商品 |
| 部分更新商品 | PATCH | /api/products/{id} | 部分更新指定ID的商品 |
| 删除商品(物理) | DELETE | /api/products/{id} | 物理删除指定ID的商品 |
| 批量删除商品(物理) | DELETE | /api/products/batch | 批量物理删除商品 |
| 删除商品(逻辑) | PATCH | /api/products/{id}/status | 逻辑删除指定ID的商品 |
| 批量删除商品(逻辑) | DELETE | /api/products/batch/status | 批量逻辑删除商品 |
| 更新商品状态 | PATCH | /api/products/{id}/status | 更新商品状态 |
| 获取商品详情 | GET | /api/products/{id} | 获取指定ID的商品详情 |
| 根据名称获取商品 | GET | /api/products/by-name | 根据名称精确查询商品 |
| 获取商品列表 | GET | /api/products | 获取所有商品 |
| 条件查询商品 | GET | /api/products/search | 根据条件查询商品 |
| 分页查询商品 | POST | /api/products/page | 分页查询商品 |

## 如何运行

1. 确保已安装JDK 8+和Maven
2. 在项目根目录执行：`mvn clean install`
3. 运行应用：`mvn spring-boot:run`
4. 访问地址：http://localhost:8080

## 示例请求

### 创建商品
```json
POST /api/products
Content-Type: application/json

{
  "name": "示例商品",
  "description": "这是一个示例商品",
  "price": 99.99,
  "stock": 100
}
```

### 批量创建商品
```json
POST /api/products/batch
Content-Type: application/json

[
  {
    "name": "示例商品1",
    "description": "这是示例商品1",
    "price": 99.99,
    "stock": 100
  },
  {
    "name": "示例商品2",
    "description": "这是示例商品2",
    "price": 199.99,
    "stock": 50
  }
]
```

### 全量更新商品
```json
PUT /api/products/1
Content-Type: application/json

{
  "name": "更新后的商品",
  "description": "这是更新后的描述",
  "price": 199.99,
  "stock": 50
}
```

### 部分更新商品
```json
PATCH /api/products/1
Content-Type: application/json

{
  "name": "部分更新的商品名称",
  "price": 299.99
}
```

### 条件查询商品
```
GET /api/products/search?name=手机&minPrice=5000&maxPrice=10000&status=1
```

### 分页查询商品
```json
POST /api/products/page?pageNum=1&pageSize=10
Content-Type: application/json

{
  "name": "手机",
  "minPrice": 5000,
  "maxPrice": 10000,
  "status": 1,
  "sortBy": "price",
  "sortOrder": "asc"
}
```

### 更新商品状态
```
PATCH /api/products/1/status
```

### 根据名称查询商品
```
GET /api/products/by-name?name=苹果手机
```

### 批量删除商品(物理)
```json
DELETE /api/products/batch
Content-Type: application/json

[1,2,3]
```

### 批量删除商品(逻辑)
```json
DELETE /api/products/batch/status
Content-Type: application/json

[1,2,3]
```