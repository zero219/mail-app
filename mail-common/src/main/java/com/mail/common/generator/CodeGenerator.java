package com.mail.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {
    public static void main(String[] args) {
        String packageName = "com.mail.admin";
        String[] tableNames = {"sys_login_log", "sys_menu", "sys_oper_log", "sys_role", "sys_role_menu", "sys_user", "sys_user_role"};

        generateFile(packageName, tableNames);
        generateQueryFiles("code-generator", packageName, tableNames);
        generatePageResultWithString("code-generator", packageName);
        generateResultCodeEnum("code-generator", packageName);
        generateResultOperationEnum("code-generator", packageName);
        generateResultClass("code-generator", packageName);
        generateDtoAndVoFiles("code-generator", packageName, tableNames);
    }

    /**
     * 代码生成器
     *
     * @param packageName
     */
    public static void generateFile(String packageName, String... tableNames) {

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
                .setInclude(tableNames); // 指定要生成的表名
        generator.setStrategy(strategyConfig);

        // 配置包名
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName) // 基础包名
                .setModuleName("")
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

    /**
     * vo 、dto
     *
     * @param outputDir
     * @param packageName
     * @param tableNames
     */
    public static void generateDtoAndVoFiles(String outputDir, String packageName, String[] tableNames) {
        for (String tableName : tableNames) {
            // 将表名转换为驼峰格式（首字母大写）
            String entityName = underlineToCamel(tableName, true);

            // ===================== 生成 Dto =====================
            String dtoClassName = entityName + "Dto";
            String dtoContent = String.format(
                    "package %s.dto;\n\n" +
                            "import lombok.Getter;\n" +
                            "import lombok.Setter;\n\n" +
                            "/**\n" +
                            " * %s 数据传输对象\n" +
                            " */\n" +
                            "@Getter\n" +
                            "@Setter\n" +
                            "public class %s {\n\n" +
                            "    private String userName; // 用户名\n" +
                            "    private String password; // 密码\n\n" +
                            "}",
                    packageName, entityName, dtoClassName
            );

            String filePathDto = outputDir + "/" + packageName.replace('.', '/') + "/dto/" + dtoClassName + ".java";
            writeToFile(filePathDto, dtoContent);

            // ===================== 生成 Vo =====================
            String voClassName = entityName + "Vo";
            String voContent = String.format(
                    "package %s.vo;\n\n" +
                            "import lombok.Getter;\n" +
                            "import lombok.Setter;\n\n" +
                            "/**\n" +
                            " * %s 视图对象\n" +
                            " */\n" +
                            "@Getter\n" +
                            "@Setter\n" +
                            "public class %s {\n\n" +
                            "    private Integer id;       // 用户 ID\n" +
                            "    private String userName;  // 用户名\n" +
                            "    private String avatar;    // 头像\n\n" +
                            "}",
                    packageName, entityName, voClassName
            );
            String filePathVo = outputDir + "/" + packageName.replace('.', '/') + "/vo/" + voClassName + ".java";
            writeToFile(filePathVo, voContent);
        }
    }

    /**
     * Query
     *
     * @param outputDir
     * @param packageName
     * @param tableNames
     */
    public static void generateQueryFiles(String outputDir, String packageName, String[] tableNames) {
        for (String tableName : tableNames) {
            // 将表名转换为驼峰格式（首字母大写）
            String entityName = underlineToCamel(tableName, true);
            String className = entityName + "Query";

            // 拼接类内容
            String content = String.format(
                    "package %s.query;\n\n" +
                            "import lombok.Getter;\n" +
                            "import lombok.Setter;\n\n" +
                            "/**\n" +
                            " * %s 查询对象\n" +
                            " */\n" +
                            "@Getter\n" +
                            "@Setter\n" +
                            "public class %s {\n\n" +
                            "    private int page = 1;       // 当前页码\n" +
                            "    private int pageSize = 10;  // 每页记录数\n" +
                            "    private Integer id;          // 示例字段，可替换为实际字段\n\n" +
                            "}",
                    packageName, entityName, className
            );

            String filePath = outputDir + "/" + packageName.replace('.', '/') + "/query/" + className + ".java";
            writeToFile(filePath, content);
        }
    }


    /**
     * ResultCodeEnum
     *
     * @param outputDir
     * @param packageName
     */
    public static void generateResultCodeEnum(String outputDir, String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".enums").append(";\n\n")
                .append("import lombok.Getter;\n\n")
                .append("@Getter\n")
                .append("public enum ResultCodeEnum {\n\n")
                .append("    SUCCESS(200, \"请求成功\"),\n")
                .append("    FAIL(500, \"请求失败\"),\n")
                .append("    UNAUTHORIZED(401, \"未认证\"),\n")
                .append("    FORBIDDEN(403, \"没有权限\"),\n")
                .append("    NOT_FOUND(404, \"接口不存在\"),\n")
                .append("    INVALID_PARAM(400, \"请求参数无效\"),\n")
                .append("    SERVER_ERROR(500, \"服务器内部错误\"),\n")
                .append("    TOO_MANY_REQUESTS(429, \"请求过于频繁\");\n\n")
                .append("    private final int code;\n")
                .append("    private final String message;\n\n")
                .append("    ResultCodeEnum(int code, String message) {\n")
                .append("        this.code = code;\n")
                .append("        this.message = message;\n")
                .append("    }\n")
                .append("}\n");

        String filePath = outputDir + "/" + packageName.replace('.', '/') + "/enums/ResultCodeEnum.java";
        writeToFile(filePath, sb.toString());
    }

    /**
     * ResultOperationEnum
     *
     * @param outputDir
     * @param packageName
     */
    public static void generateResultOperationEnum(String outputDir, String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".enums").append(";\n\n")
                .append("import lombok.Getter;\n\n")
                .append("@Getter\n")
                .append("public enum ResultOperation {\n\n")
                .append("    FAIL(0, \"操作失败\"),\n")
                .append("    SUCCESS(1, \"操作成功\");\n\n")
                .append("    private final int code;\n")
                .append("    private final String msg;\n\n")
                .append("    ResultOperation(int code, String msg) {\n")
                .append("        this.code = code;\n")
                .append("        this.msg = msg;\n")
                .append("    }\n")
                .append("}\n");

        String filePath = outputDir + "/" + packageName.replace('.', '/') + "/enums/ResultOperation.java";
        writeToFile(filePath, sb.toString());
    }

    /**
     * Result
     *
     * @param outputDir
     * @param packageName
     */
    public static void generateResultClass(String outputDir, String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".core").append(";\n\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.Setter;\n\n")
                .append("@Getter\n")
                .append("@Setter\n")
                .append("public class Result<T> {\n\n")
                .append("    /**\n")
                .append("     * 返回码\n")
                .append("     */\n")
                .append("    private int code;\n\n")
                .append("    /**\n")
                .append("     * 返回消息\n")
                .append("     */\n")
                .append("    private String message;\n\n")
                .append("    /**\n")
                .append("     * 返回数据\n")
                .append("     */\n")
                .append("    private T data;\n\n")
                .append("    /**\n")
                .append("     * 是否成功\n")
                .append("     */\n")
                .append("    private boolean success;\n\n")
                .append("    public Result() {}\n\n")
                .append("    public Result(int code, String message, T data, boolean success) {\n")
                .append("        this.code = code;\n")
                .append("        this.message = message;\n")
                .append("        this.data = data;\n")
                .append("        this.success = success;\n")
                .append("    }\n\n")
                .append("    /**\n")
                .append("     * 成功返回方法（含数据）\n")
                .append("     */\n")
                .append("    public static <T> Result<T> success(T data) {\n")
                .append("        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), data, true);\n")
                .append("    }\n\n")
                .append("    /**\n")
                .append("     * 成功返回方法（无数据）\n")
                .append("     */\n")
                .append("    public static <T> Result<T> success() {\n")
                .append("        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultOperation.SUCCESS.getMsg(), null, true);\n")
                .append("    }\n\n")
                .append("    /**\n")
                .append("     * 失败返回方法（默认错误信息）\n")
                .append("     */\n")
                .append("    public static <T> Result<T> fail() {\n")
                .append("        return new Result<>(ResultCodeEnum.FAIL.getCode(), ResultOperation.FAIL.getMsg(), null, false);\n")
                .append("    }\n\n")
                .append("    /**\n")
                .append("     * 失败返回方法（自定义信息）\n")
                .append("     */\n")
                .append("    public static <T> Result<T> fail(String message) {\n")
                .append("        return new Result<>(ResultCodeEnum.FAIL.getCode(), message, null, false);\n")
                .append("    }\n\n")
                .append("    /**\n")
                .append("     * 失败返回方法（自定义枚举和信息）\n")
                .append("     */\n")
                .append("    public static <T> Result<T> fail(int code, String message) {\n")
                .append("        return new Result<>(code, message, null, false);\n")
                .append("    }\n")
                .append("}\n");

        String filePath = outputDir + "/" + packageName.replace('.', '/') + "/core/Result.java";
        writeToFile(filePath, sb.toString());
    }

    /**
     * PageResult
     *
     * @param outputDir
     * @param packageName
     */
    public static void generatePageResultWithString(String outputDir, String packageName) {
        // 拼接包声明和类内容
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(".core").append(";\n\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.Setter;\n")
                .append("import java.util.List;\n\n")
                .append("/**\n")
                .append(" * 通用分页返回结果\n")
                .append(" */\n")
                .append("@Getter\n")
                .append("@Setter\n")
                .append("public class PageResult<T> {\n")
                .append("    private int total;       // 总记录数\n")
                .append("    private int page;        // 当前页码\n")
                .append("    private int pageSize;    // 每页条数\n")
                .append("    private int totalPages;  // 总页数\n")
                .append("    private T records; // 当前页数据\n\n")
                .append("    public PageResult(int total, int page, int pageSize, int totalPages, T records) {\n")
                .append("        this.total = total;\n")
                .append("        this.page = page;\n")
                .append("        this.pageSize = pageSize;\n")
                .append("        this.totalPages = totalPages;\n")
                .append("        this.records = records;\n")
                .append("    }\n")
                .append("}\n");

        // 计算文件输出路径
        String filePath = outputDir + "/" + packageName.replace('.', '/') + "/core/PageResult.java";
        writeToFile(filePath, sb.toString());
    }

    /**
     * 将表名转换为驼峰格式（首字母大写）
     *
     * @param name            表名
     * @param capitalizeFirst bool
     * @return String
     */
    public static String underlineToCamel(String name, boolean capitalizeFirst) {
        StringBuilder result = new StringBuilder();
        boolean toUpper = capitalizeFirst;
        for (char c : name.toCharArray()) {
            if (c == '_') {
                toUpper = true;
            } else {
                result.append(toUpper ? Character.toUpperCase(c) : c);
                toUpper = false;
            }
        }
        return result.toString();
    }

    /**
     * 生成文件
     *
     * @param filePath
     * @param content
     */
    private static void writeToFile(String filePath, String content) {
        File outputFile = new File(filePath);

        // 确保父目录存在
        outputFile.getParentFile().mkdirs();

        // 写入文件
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(content);
            System.out.println("PageResult.java 生成成功，路径：" + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
