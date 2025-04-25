package com.mail.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

public class CodeGenerator {
    public static void main(String[] args) {
        // 创建代码生成器对象
        AutoGenerator generator = new AutoGenerator();
        // 配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf-8")
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
                .setInclude("tbl_book"); // 指定要生成的表名
        generator.setStrategy(strategyConfig);

        // 配置包名
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.example") // 基础包名
                .setEntity("entity") // 实体类包名
                .setMapper("mapper") // Mapper 包名
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
}
