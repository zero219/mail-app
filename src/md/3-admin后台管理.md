#### admin后台管理

执行sql脚本`admin.sql`

创建`mail-admin`模块

添加依赖

```pom
<!--有些springboot项目创建时没有引入，引入即可-->
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

 <!-- MyBatis-Plus 核心依赖 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>

        <!-- MySQL 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version>
        </dependency>

        <!-- Druid 数据源依赖 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.24</version>
        </dependency>

        <!-- Lombok 依赖 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
        </dependency>

        <!-- MyBatis-Plus 代码生成器依赖 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.1</version>
        </dependency>
        <!-- Velocity依赖 -->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-engine-core</artifactId>
            <version>2.3</version>
        </dependency>
```

使用mybatis-plus代码生成器

``` java
public class CodeGenerator {
    public static void main(String[] args) {
        // 创建代码生成器对象
        AutoGenerator generator = new AutoGenerator();
        // 配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mail_admin?useUnicode=true&characterEncoding=utf-8")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("123456")
                .setDbType(DbType.MYSQL); // 数据库类型
        generator.setDataSource(dataSourceConfig);

        // 配置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("code-generator") // 生成文件的输出目录
                .setAuthor("123") // 作者
                .setOpen(false) // 是否打开生成文件夹
                .setFileOverride(true) // 是否覆盖已有文件
                .setServiceName("%sService"); // Service 命名方式，去掉 "I" 前缀
        generator.setGlobalConfig(globalConfig);

        // 配置策略
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel) // 表名生成策略（下划线转驼峰）
                .setColumnNaming(NamingStrategy.underline_to_camel) // 字段名生成策略
                .setEntityLombokModel(true) // 使用 Lombok
                .setRestControllerStyle(true) // 是否生成 RestController 风格
                .setInclude("sys_user","sys_login_log"); // 指定要生成的表名
        generator.setStrategy(strategyConfig);

        // 配置包名
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.mail.admin") // 基础包名
                .setEntity("entity") // 实体类包名
                .setMapper("mapper") // Mapper 包名
                .setXml("mapper.mapper")
                .setService("service") // Service 包名
                .setController("controller"); // Controller 包名
        generator.setPackageInfo(packageConfig);

        // 配置模板引擎
        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("resource.loader", "class");
        velocityProperties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(velocityProperties);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("/templates/entity.java.vm") // 配置实体类模板
                .setMapper("/templates/mapper.java.vm") // 配置Mapper接口模板
                .setXml("/templates/mapper.xml.vm") // 配置Mapper XML模板
                .setService("/templates/service.java.vm") // 配置Service接口模板
                .setServiceImpl("/templates/serviceImpl.java.vm") // 配置Service实现类模板
                .setController("/templates/controller.java.vm"); // 配置Controller模板

        // 将模板配置设置到生成器中
        generator.setTemplate(templateConfig);

        // 执行代码生成
        generator.execute();
    }
```

templates文件夹中的模板

配置文件

使用Knife4j文档  
地址`http://localhost:9002/doc.htm`





