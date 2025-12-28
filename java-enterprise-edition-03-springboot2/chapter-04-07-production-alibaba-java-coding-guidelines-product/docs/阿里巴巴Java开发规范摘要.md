# 阿里巴巴Java开发规范摘要

## 1. DTO/BO/VO命名规范

### 1.1. 核心命名思路（按优先级从高到低排列）
- 统一大于绝对：整体命名要统一，不应该出现割裂的命名风格
- 约定优于配置：尽量以阿里巴巴Java开发规范为基础
- 符合代码自解释原则：即见名知意

### 1.2. 核心语义前置：ProductCreateDTO 是更合适的命名
- ProductCreateDTO：核心词是Product（实体/领域对象），Create表示操作类型，DTO表示数据对象类型。这种"主体+操作+类型"的结构更符合英语自然阅读习惯（如"产品创建DTO"）。
- CreateProductDTO：动词前置（Create）更像是"创建产品的DTO"，将操作放在主体前，降低了领域对象的突出性，在复杂业务中可能降低可读性。

### 1.3. 领域/业务驱动命名：强调业务实体为核心，这样可以提升代码可读性

### 1.4. 命名规范示例
- DTO
    - 产品创建 ProductCreateDTO
    - 产品删除 ProductDeleteDTO
    - 产品更新 ProductUpdateDTO
    - 产品查询 ProductQueryDTO
- VO
    - 产品VO   ProductVO

## 2. 方法命令规范
- 动词 + 名词 + 驼峰命名
    - 创建产品 createProduct
    - 删除产品 deleteProductById
    - 查询产品 getProductById
    - 逻辑删除 softDeleteProductById

## 3. 属性的注释格式

### 3.1. 格式
```java
/** 商品名称 */
private String name;
```

### 3.2. 原因
- 符合 Java 注释规范，方便 IDE 自动生成文档。
- 写在一行，节省空间，避免注释内容过长。

## 4. 逻辑删除
### 4.1. 逻辑删除字段使用Boolean还是Integer
- 禁止用 Boolean 类型：布尔类型表示状态仅能表示 “是 / 否”，但逻辑删除字段本质是「状态标识」，可能存在扩展场景（如：0 = 未删除、1 = 已删除、2 = 回收站、3 = 永久删除）
- 优先使用 Integer 类型：阿里规范明确倾向，Integer 类型具备扩展性
- 避免空值语义模糊问题：Boolean 类型的null易引发歧义（是 “未设置” 还是 “未删除”？），而 Integer 可通过默认值（如 0）明确语义。
- 避免数据库层面的兼容性问题：部分数据库（如 MySQL）的 BOOLEAN 类型本质是 TINYINT (1)，存储时仍为数字，Java 中用 Boolean 接收易出现类型转换问题（如 MyBatis 映射异常）

### 4.2. 逻辑删除注意事项
- 统一处理：在DAO层统一处理逻辑删除过滤，避免在每个查询中重复编写
- 索引优化：为is_deleted字段添加索引以提高查询性能
- 定期清理：制定策略定期清理长期未使用的逻辑删除数据
- 权限控制：只有特定角色才能执行真正的物理删除操作
- 时间戳记录：记录删除时间以便后续处理和审计

## 5. 通用编码规范
- 消除魔法值：用枚举封装固定值，提升可维护性；
- 参数校验完整：基础校验 + 业务规则校验，避免非法参数；
- 复杂入参封装：超过 3 个入参时封装为 DTO，符合分层思想；
- 默认值明确：可选参数无空值，避免空指针风险。

## 6.包结构规范
- util包: 存放与业务无关的通用工具（util为业界惯例）
    - Apache Commons系列使用 util（如 org.apache.commons.lang3.util）
    - Spring Framework也使用 util（如 org.springframework.util）
    - Google Guava同样使用 util（如 com.google.common.util）
- config包: 存放应用配置相关类
- constant包: 存放所有常量类
- common包: 存放与业务相关的公共组件
- enums包: 存放所有枚举类