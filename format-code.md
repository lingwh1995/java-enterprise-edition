# Java 代码格式化规范

> 本规范基于阿里巴巴 Java 开发手册（嵩山版）与 Javadoc 标准，结合项目实际约定整理而成，适用于所有 Java 源码文件的格式化处理。

---

## 一、缩进与空白

### 1.1 缩进方式

- 统一使用 **4 个空格** 作为缩进单位，禁止使用 Tab 字符
- 一个缩进层级 = 4 个空格

### 1.2 类大括号后空行

`class` / `interface` / `enum` 声明的 `{` 之后，必须**空一行**，再开始书写第一个属性、方法或注释。

正确示例：

```java
public class FlyweightFactory {

    private static FlyweightFactory factory = new FlyweightFactory();

    private FlyweightFactory() {}

    public static FlyweightFactory getInstance() {
        return factory;
    }
}
```

错误示例：

```java
public class FlyweightFactory {
    private static FlyweightFactory factory = new FlyweightFactory();
}
```

### 1.3 类成员分隔

- 类内部的成员（属性、方法、构造器、内部类）之间，**必须用一个空行分隔**
- 类大括号 `{` 后必须空一行
- **结束大括号 `}` 前不空行**（最后一个成员与类结束 `}` 之间不留空行）

### 1.4 花括号风格

采用 K&R 风格（Egyptian style）：

- 左花括号 `{` 不换行，跟在当前行末尾
- 右花括号 `}` 独立成行

---

## 二、类文档注释格式

### 2.1 标准格式

类文档注释（Javadoc）**必须**遵循以下结构：

```java
/**
 * 描述文本（取自原 @desc 值，或根据类名/类功能自动生成）
 *
 * @author 作者名
 * @date 创建时间
 */
public class XxxClass {
```

### 2.2 格式要点

1. **描述置顶**：类的功能描述必须放在 Javadoc 的第一行（`* ` 后），而非使用 `@desc` 标签
2. **空行分隔**：描述文本与 `@author` 之间必须有一个空行（仅 `* ` 的行）
3. **删除 `@desc`**：原 `@desc` 标签必须删除，其值提取到描述位置
4. **删除其他非标准标签**：`@version`、`@since` 等标签必须删除
5. **保留标签**：仅保留 `@author` 和 `@date`

### 2.3 转换示例

转换前：

```java
/**
 * @author lingwh
 * @desc 奥迪汽车
 * @date 2019/3/10 00:00
 */
public class Audi implements Car {
```

转换后：

```java
/**
 * 奥迪汽车
 *
 * @author lingwh
 * @date 2019/3/10 00:00
 */
public class Audi implements Car {
```

### 2.4 无 @desc 或 @desc 为空的处理

- 若原文件无 `@desc` 标签，或 `@desc` 后无值，则根据类名和类的代码内容**自动生成**简短描述
- 描述应为中文，简明扼要，概括类的职责或功能

示例：

```java
/**
 * 简单工厂中创建车的工厂类
 *
 * @author lingwh
 * @date 2026/7/9 14:30
 */
public class CarFactory {
```

### 2.5 接口与枚举

接口、枚举、注解类型的文档注释规则与类一致。

---

## 三、方法注释格式

### 3.1 单行注释转多行

方法的单行 Javadoc 注释（`/** xxx */`）必须转换为多行格式。

转换前：

```java
/** 简单工厂中创建车的方法 */
public static Car createCar(String carName) {
```

转换后：

```java
/**
 * 简单工厂中创建车的方法
 *
 * @param carName
 * @return
 */
public static Car createCar(String carName) {
```

### 3.2 带参数的方法注释

带 `@param`、`@return`、`@throws` 的方法注释格式：

```java
/**
 * 获取key对应的享元对象
 *
 * @param key 获取享元对象的key
 * @return 对应的享元对象
 */
public Flyweight getFlyweight(String key) {
```

### 3.3 无参数的方法注释格式

```java
/**
 * 计算工资，其实应该有很多参数，为了演示从简
 */
public void calcPay() {
```

### 3.4 方法体内的行内注释格式

方法体内的 `//` 行内注释需遵循以下格式规则（仅格式调整，不改内容）：

1. **`//` 后必须加一个空格**：`//注释文字` → `// 注释文字`
2. **编号列表格式**：`数字.文字` → `数字. 空格 文字`（如 `1.创建ServerSocket` → `1. 创建ServerSocket`）

转换前：

```java
public void start() {
    //1.创建ServerSocket
    ServerSocket serverSocket = new ServerSocket(8080);
    // 2.等待客户端连接
    Socket socket = serverSocket.accept();
}
```

转换后：

```java
public void start() {
    // 1. 创建ServerSocket
    ServerSocket serverSocket = new ServerSocket(8080);
    // 2. 等待客户端连接
    Socket socket = serverSocket.accept();
}
```

---

## 四、属性注释格式

### 4.1 单行注释转多行

属性的单行 Javadoc 注释必须转换为多行格式，与方法注释规则一致。

转换前：

```java
/** 英雄昵称 */
private String nickName;
```

转换后：

```java
/**
 * 英雄昵称
 */
private String nickName;
```

### 4.2 多个属性示例

```java
public class Hero {

    /**
     * 英雄昵称
     */
    private String nickName;

    /**
     * 英雄等级
     */
    private Integer level;

    /**
     * 英雄血量
     */
    private Integer hp;
}
```

---

## 五、导包顺序规范

### 5.1 分组规则

`import` 语句按以下顺序分组，**组与组之间用一个空行分隔**：

1. **第一组**：第三方包（非 `java.*` / `javax.*` 的包）
2. **第二组**：JDK 内部包（`java.*` / `javax.*`）

### 5.2 完整示例

```java
package org.bluebridge.xml.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * 使用dom4j解析xml
 *
 * @author lingwh
 * @date 2026/4/21 19:02
 */
public class Dom4jParseXml {
```

### 5.3 特殊情况

- **仅有 JDK 包**：无需分组，直接书写即可，导包与类文档注释之间仍需空一行
- **仅有第三方包**：同上，无需空行分组
- **单一类型导入**：不需要分组
- `package` 语句与第一个 `import` 之间**不空行**
- 最后一组 `import` 与类文档注释之间**必须空一行**

仅 JDK 包示例：

```java
package action.iterator.iterator_d;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户方已有的工资管理对象
 *
 * @author lingwh
 * @date 2019/8/20 9:17
 */
public class PayManager extends Aggregate {
```

---

## 六、@author 标签规范

### 6.1 统一作者名

- 所有 `@author` 标签的值统一为 `lingwh`
- 遇到 `@author ronin` 必须替换为 `@author lingwh`

### 6.2 示例

转换前：

```java
 * @author ronin
```

转换后：

```java
 * @author lingwh
```

---

## 七、@date 标签规范

### 7.1 格式

`@date` 标签使用 `yyyy/M/d HH:mm` 格式：

```java
 * @date 2019/3/10 14:30
```

### 7.2 时间为 00:00 的处理

当 `@date` 的时间部分为 `00:00` 时，按以下步骤处理：

1. **查询 Git 文件创建时间**：
   ```bash
   git log --diff-filter=A --format="%ad" --date=format:"%Y/%-m/%-d %H:%M" -- <文件路径>
   ```
2. **使用 Git 时间**：将 `@date` 的值替换为 Git 查询到的文件创建时间
3. **Git 时间仍为 00:00 的兜底处理**：若 Git 查询到的时间仍为 `00:00`，则随机生成一个合法时间值（小时范围 08-22，分钟范围 00-59）

### 7.3 时间非 00:00 的处理

若 `@date` 的时间部分**不是** `00:00`，则保留原值不动。

### 7.4 日期大于 2026/7/1 的修复

当 `@date` 的年月日部分大于 `2026/7/1` 时（这些日期是之前错误生成的），按以下步骤修复：

1. **查找同包最小日期**：扫描该文件所在目录（同一 package）下所有 Java 文件的 `@date` 值，找出年月日部分小于等于 `2026/7/1` 的最小日期
2. **替换年月日**：将错误文件的 `@date` 年月日部分替换为该最小日期
3. **随机生成时间**：时间部分随机生成一个合法值（小时范围 08-22，分钟范围 00-59）
4. **同包无有效日期的兜底**：若同包内所有文件的 `@date` 都大于 `2026/7/1`，则递归查找上级目录或相邻包中的有效日期

### 7.5 新建文件

新建文件的 `@date` 使用当前日期，时间部分若为 `00:00` 则随机生成。

---

## 十一、注释文字排版规范

本节规则适用于所有 Java 文件中的注释文字（Javadoc 注释和 `//` 行内注释），**不修改注释的文字内容，仅调整标点和空格**。

### 11.1 英文逗号改中文逗号

注释文字中的英文逗号 `,` 一律改为中文逗号 `，`。

- `缓存,以后再查询` → `缓存，以后再查询`
- `数组形式,可以指定多个名称` → `数组形式，可以指定多个名称`

**例外**：代码示例中的逗号不修改（如 `@CachePut(cacheNames={"user"},key="#user.id")`）。

### 11.2 英文冒号改中文冒号

注释文字中，凡跟在文字（字母、汉字、数字）后面的冒号一律改为中文冒号 `：`。

- `工作机制:` → `工作机制：`
- `cacheNames:` → `cacheNames：`
- `sync: 是否异步` → `sync：是否异步`

**例外**：以下情况不修改：
- URL 中的冒号：`http://`、`https://`
- 代码示例中的冒号：`key="#id":` 中的冒号
- 跟在符号后面的冒号：`="true":` 中的冒号

### 11.3 英文和数字前后加空格

注释文字中，当英文单词或数字与中文字符相邻时，在英文/数字与中文之间添加一个空格。

- `sqlSession级别的缓存` → `sqlSession 级别的缓存`
- `id不等于002` → `id 不等于 002`
- `spel表达式` → `spel 表达式`
- `@Cacheable注解` → `@Cacheable 注解`
- `Cache组件` → `Cache 组件`
- `二级缓存` → `二级缓存`（"二"是中文数字，不处理）
- `1.spel` → `1. spel`（数字. 后已有空格规则处理）

**例外**：以下情况不添加空格：
- 代码示例中的英文/数字：`key="#id"` 中的 `id`
- 连续的英文/数字之间不添加空格：`cacheEnabled=true`
- `@param`、`@return`、`@author`、`@date` 等 Javadoc 标签中的内容不修改

---

## 八、完整文件示例

以下是一个符合本规范的完整 Java 文件示例：

```java
package structure.flyweight.flyweight_e;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 *
 * @author lingwh
 * @date 2019/8/1 13:53
 */
public class FlyweightFactory {

    private static FlyweightFactory factory = new FlyweightFactory();

    private FlyweightFactory() {}

    public static FlyweightFactory getInstance() {
        return factory;
    }

    /**
     * 缓存多个flyweight对象
     */
    private Map<String, Flyweight> fsMap = new HashMap<String, Flyweight>();

    /**
     * 获取key对应的享元对象
     *
     * @param key 获取享元对象的key
     * @return 对应的享元对象
     */
    public Flyweight getFlyweight(String key) {
        Flyweight flyweight = fsMap.get(key);
        if (flyweight == null) {
            flyweight = new AuthorizationFlyweight(key);
            fsMap.put(key, flyweight);
        }
        return flyweight;
    }
}
```

---

## 九、跳过规则

以下文件**不进行**格式化处理：

1. `module-info.java` 文件
2. 方法体内的 `/* */` 块注释保持原样
3. 字段级别的非 Javadoc 注释（`//` 格式的行内注释保持原样）

> 注意：方法体内的 `//` 行内注释需按 3.4 节规则进行格式调整（`//` 后加空格、编号列表加空格），但注释内容不做修改。

---

## 十、规范速查表

| 序号 | 规则         | 说明                                                 |
| ---- | ------------ | ---------------------------------------------------- |
| 1    | 缩进         | 4 个空格，禁止 Tab                                   |
| 2    | 类大括号后   | `{` 后空一行，`}` 前不空行                         |
| 3    | 类成员分隔   | 成员间空一行                                         |
| 4    | 类文档注释   | 描述置顶，删除 `@desc`，保留 `@author`/`@date` |
| 5    | 方法单行注释 | `/** xxx */` → 多行格式                           |
| 6    | 属性单行注释 | `/** xxx */` → 多行格式                           |
| 7    | 导包顺序     | 第三方包 → 空行 → JDK 包 → 空行 → 类注释         |
| 8    | @author      | 统一为 `lingwh`，`ronin` 需替换                  |
| 9    | @date 时间   | `00:00` 需从 Git 查询，仍为 `00:00` 则随机生成   |
| 10   | 方法体注释   | `//` 后加空格，`数字.` 后加空格                   |
| 11   | 注释排版     | 英文逗号→中文逗号，文字后冒号→中文冒号，英文/数字前后加空格 |
| 12   | @date 修复   | 年月日 > 2026/7/1 的，取同包最小日期 + 随机时间     |
| 13   | 跳过文件     | `module-info.java` 不处理                          |
