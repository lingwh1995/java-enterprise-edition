package org.bluebridge;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

/**
 * MybatisPlus代码生成器测试
 */
public class MybatisPlusGeneratorTest {
    // 数据库连接字段配置
    private static final String URL = "jdbc:mysql://host.docker.internal:3306/javaee";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    // 包名和模块名
    private static final String PACKAGE_NAME = "com.xa8it";
    private static final String MODULE_NAME = "xxx";

    // 表名,多个表使用英文逗号分割
    private static final String[] TBL_NAMES = {"t_employee"};

    // 表名的前缀,从表生成代码时会去掉前缀
    private static final String TABLE_PREFIX = "t_";


    public static void main(String[] args) {
        //获取当前工程路径(这里无需修改)
        String projectPath = System.getProperty("user.dir");

        /**
         * 1.数据库配置(设置数据源)
         */
        DataSourceConfig.Builder dataSourceConfigBuilder =
                new DataSourceConfig.Builder(URL, USERNAME, PASSWORD)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());

        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder);


        /**
         * 2.全局配置
         */
        fastAutoGenerator.globalConfig(
                globalConfigBuilder -> globalConfigBuilder
                        .disableOpenDir()   // 不打开生成文件目录
                        .outputDir(projectPath + "/mybatis-plus-generator/src/main/java") // 指定输出目录,注意斜杠的表示
                        .author("lingwh") // 设置注释的作者
                        //.commentDate("yyyy-MM-dd HH:mm:ss") // 设置注释的日期格式
                        //.dateType(DateType.TIME_PACK)   // 使用java8新的时间类型
//                        .enableSwagger()    // 开启swagger文档
        );

        /**
         * 3.包配置
         */
        fastAutoGenerator.packageConfig(
                packageConfigBuilder -> packageConfigBuilder
                        .parent(PACKAGE_NAME)   // 设置父包名
                        // .moduleName(MODULE_NAME) // 设置父包模块名
                        .entity("entity") // 设置MVC下各个模块的包名
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .xml( "/src/main/resources/mapper") // 设置XML资源文件的目录
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/mybatis-plus-generator/src/main/resources/mapper"))  // 设置XML资源文件的目录
        );


        /**
         * 4.策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder
                        .enableCapitalMode()    // 开启大写命名
                        .enableSkipView()   // 开启跳过视图
                        .disableSqlFilter() // 禁用sql过滤
                        .addInclude(TBL_NAMES)  // 设置需要生成的表名
                        .addTablePrefix(TABLE_PREFIX)   // 设置过滤表前缀
        );


        /**
         * 4.1 Entity策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.entityBuilder()
                        .enableFileOverride()   // 开启文件覆盖
                        .enableTableFieldAnnotation()   // 生成实体时生成字段的注解，包括@TableId注解等---
                        .naming(NamingStrategy.underline_to_camel)  // 数据库表和字段映射到实体的命名策略,为下划线转驼峰
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .idType(IdType.AUTO)    // 全局主键类型为AUTO(自增)
                        .enableLombok() // 支持lombok开启注解
                        .logicDeleteColumnName("deleted")   // 逻辑删除字段名(数据库)
                        .logicDeletePropertyName("deleted") // 逻辑删除属性名(实体)
                        .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置  create_time  update_time 两种方式
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        .versionColumnName("version")   // 开启乐观锁
                        .disableSerialVersionUID()  // 禁用生成 serialVersionUID，默认值:true
                        .enableChainModel() // 开启实体类链式编程
                        //.formatFileName("%sEntity") // 实体名称格式化为XXXEntity   formatFileName("%sEntity")
                        .formatFileName("%s") // 实体名称格式化为XXXEntity   formatFileName("%sEntity")
                        .enableTableFieldAnnotation()
        );

        /**
         * 4.2 Controller策略配置
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.controllerBuilder()
                        .enableFileOverride() // 开启文件覆盖
                        .enableRestStyle()  // 开启生成@RestController控制器
                        //.enableHyphenStyle()    // 开启驼峰转连字符 localhost:8080/hello_id_2
        );

        /**
         * 4.3 Service策略配置
         格式化service接口和实现类的文件名称，去掉默认的ServiceName前面的I ----
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.serviceBuilder()
                        .enableFileOverride() // 开启文件覆盖
                        .formatServiceFileName("I%sService")
                        .formatServiceImplFileName("%sServiceImpl")
        );

        /**
         * 4.4 Mapper策略配置
         格式化 mapper文件名,格式化xml实现类文件名称
         */
        fastAutoGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.mapperBuilder()
                        .enableFileOverride() // 开启文件覆盖
                        .enableMapperAnnotation()   // 开启 @Mapper 注解
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
        );   // 开启文件覆盖

        /** 5.生成代码
         *
         */
        fastAutoGenerator.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();
    }
}