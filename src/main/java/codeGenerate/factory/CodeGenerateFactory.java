package codeGenerate.factory;

import codeGenerate.ColumnData;
import codeGenerate.CommonPageParser;
import codeGenerate.CreateBean;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FreemarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>zhoujf<br>
 * <b>日期：</b> Fed 16, 2013 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class CodeGenerateFactory {
    private static final Logger log = LoggerFactory.getLogger(CodeGenerateFactory.class);
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    //项目跟路径路径，此处修改为你的项目路径
    //private static String projectPath = "F:\\project\\lvyou\\workspace-jeecg\\YIYA_SERVICE\\";//getRootPath();// "F:\\openwork\\open\\";

    //业务包
    private static String buss_package = CodeResourceUtil.bussiPackage;
//	private static String projectPath = getProjectPath();


    /**
     * 单表（代码生成方法）
     *
     * @param tableName 表名
     * @param codeName  功能描述
     * @param keyType   主键类型
     */
    public static void codeGenerateByVM(String tableName, String codeName, String keyType) {
        try {
            CreateBean createBean = new CreateBean();
            createBean.setMysqlInfo(url, username, passWord);
            /** 此处修改成你的 表名 和 中文注释***/
            String className = createBean.getTablesNameToClassName(tableName);
            String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
            String tableNameUpper = tableName.toUpperCase();
            String tablesAsName = createBean.getTablesASName(tableName);
            if (StringUtils.isEmpty(codeName)) {
                Map<String, String> tableCommentMap = createBean.getTableCommentMap();
                codeName = tableCommentMap.get(tableNameUpper);
            }
            String sqlmapPathSrc = CodeResourceUtil.getConfigInfo("sqlmap_path_src");
            String domainPathSrc = CodeResourceUtil.getConfigInfo("domain_path_src");
            String daoPathSrc = CodeResourceUtil.getConfigInfo("dao_path_src");
            String daoImplPathSrc = CodeResourceUtil.getConfigInfo("dao_impl_path_src");
            String managerPathSrc = CodeResourceUtil.getConfigInfo("manager_path_src");
            String managerImplPathSrc = CodeResourceUtil.getConfigInfo("manager_impl_path_src");
            String servicePathSrc = CodeResourceUtil.getConfigInfo("service_path_src");
            String serviceImplPathSrc = CodeResourceUtil.getConfigInfo("service_impl_path_src");

            String sqlmapPackage = CodeResourceUtil.getConfigInfo("sqlmap_path");
            String domainPackage = CodeResourceUtil.getConfigInfo("domain_path");
            String daoImplPackage = CodeResourceUtil.getConfigInfo("dao_impl_path");
            String mapperPackage = CodeResourceUtil.getConfigInfo("mapper_path");
            String managerPackage = CodeResourceUtil.getConfigInfo("manager_path");
            String managerImplPackage = CodeResourceUtil.getConfigInfo("manager_impl_path");
            String servicePackage = CodeResourceUtil.getConfigInfo("service_path");
            String serviceImplPackage = CodeResourceUtil.getConfigInfo("service_impl_path");

            //java,xml文件名称
            String sqlMapperPath = sqlmapPackage.replace(".", "/") + "/" + tableNameUpper + ".xml";
            String domainPath = domainPackage.replace(".", "/") + "/" + className + ".java";
            String mapperPath = mapperPackage.replace(".", "/") + "/" + className + "Mapper.java";
            String managerPath = managerPackage.replace(".", "/") + "/" + className + "Manager.java";
            String managerImplPath = managerImplPackage.replace(".", "/") + "/" + className + "ManagerImpl.java";
            String servicePath = servicePackage.replace(".", "/") + "/" + className + "Service.java";
            String serviceImplPath = serviceImplPackage.replace(".", "/") + "/" + className + "ServiceImpl.java";

            String sqlMapperFlag = CodeResourceUtil.getConfigInfo("sqlmap_flag");
            String domainFlag = CodeResourceUtil.getConfigInfo("domain_flag");
            String managerFlag = CodeResourceUtil.getConfigInfo("manager_flag");
            String managerImplFlag = CodeResourceUtil.getConfigInfo("manager_impl_flag");
            String serviceFlag = CodeResourceUtil.getConfigInfo("service_flag");
            String serviceImplFlag = CodeResourceUtil.getConfigInfo("service_impl_flag");


            VelocityContext context = new VelocityContext();
            context.put("className", className); //
            context.put("lowerName", lowerName);
            context.put("codeName", codeName);
            context.put("tableName", tableName);
            context.put("tableNameUpper", tableNameUpper);
            context.put("tablesAsName", tablesAsName);
            context.put("bussPackage", buss_package);
            context.put("domainPackage", domainPackage);
            context.put("daoImplPackage", daoImplPackage);
            context.put("mapperPackage", mapperPackage);
            context.put("managerPackage", managerPackage);
            context.put("managerImplPackage", managerImplPackage);
            context.put("servicePackage", servicePackage);
            context.put("serviceImplPackage", serviceImplPackage);
            context.put("keyType", keyType);

            /******************************生成bean字段*********************************/
            context.put("feilds", createBean.getBeanFeilds(tableName, className)); //生成bean

            /*******************************生成sql语句**********************************/
            Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
            List<ColumnData> columnDatas = createBean.getColumnDatas(tableName);
            context.put("columnDatas", columnDatas); //生成bean
            List<ColumnData> columnKeyDatas = createBean.getColumnKeyDatas(columnDatas);
            context.put("columnKeyDatas", columnKeyDatas); //生成bean
            context.put("SQL", sqlMap);

            //-------------------生成文件代码---------------------/
            if ("Y".equals(sqlMapperFlag)) {
                CommonPageParser.WriterPage(context, "sqlmap.vm", sqlmapPathSrc, sqlMapperPath);//生成sqlmap.xml配置文件
            }
            if ("Y".equals(domainFlag)) {
                CommonPageParser.WriterPage(context, "domainClass.vm", domainPathSrc, domainPath);  //生成实体Bean
            }
            if ("Y".equals(managerFlag)) {
                CommonPageParser.WriterPage(context, "managerClass.vm", managerPathSrc, managerPath); //生成manager接口
            }
            if ("Y".equals(managerImplFlag)) {
                CommonPageParser.WriterPage(context, "managerImplClass.vm", managerImplPathSrc, managerImplPath); //生成manager接口实现
            }
            if ("Y".equals(serviceFlag)) {
                CommonPageParser.WriterPage(context, "serviceClass.vm", servicePathSrc, servicePath); //生成service接口
            }
            if ("Y".equals(serviceImplFlag)) {
                CommonPageParser.WriterPage(context, "serviceImplClass.vm", serviceImplPathSrc, serviceImplPath); //生成service接口实现
            }
            log.info("----------------------------代码生成完毕---------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 单表（代码生成方法）
     * 生成代码包括：1--表对应的sqlMap文件，entity,dao,dao.impl,service,service.impl,vo,vm。等文件
     *
     * @param tableName 表名
     * @param codeName  功能描述
     * @param keyType   主键类型
     */
    public static void codeGenerateByFTL(String tableName, String codeName, String keyType) {
        try {
            CreateBean createBean = new CreateBean();
            createBean.setMysqlInfo(url, username, passWord);
            /** 此处修改成你的 表名 和 中文注释***/
            String className = createBean.getTablesNameToClassName(tableName);
            String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
            String tableNameUpper = tableName.toUpperCase();
            String tablesAsName = createBean.getTablesASName(tableName);
            if (StringUtils.isEmpty(codeName)) {
                Map<String, String> tableCommentMap = createBean.getTableCommentMap();
                codeName = tableCommentMap.get(tableNameUpper);
            }

            String pathSrc = CodeResourceUtil.getConfigInfo("path_src");//从配置文件读
            String basePackage = CodeResourceUtil.getConfigInfo("base_package");
            String bussiPackage = CodeResourceUtil.getConfigInfo("bussi_package");
            String author = CodeResourceUtil.getConfigInfo("author");

            String sqlmapPackage = "resources.sqlmap";
            String domainPackage = basePackage + "." + bussiPackage + ".entity";//com.qunar.pay.g2.fpp.system.entity
            String mapperPackage = basePackage + "." + bussiPackage + ".mapper";//com.qunar.pay.g2.fpp.system.mapper

            String servicePackage = basePackage + "." + bussiPackage + ".service";//com.qunar.pay.g2.fpp.system.service
            String serviceImplPackage = basePackage + "." + bussiPackage + ".service.impl";//com.qunar.pay.g2.fpp.system.service.impl
            String controllerPackage = basePackage + ".web." + bussiPackage;//com.qunar.pay.g2.fpp.web.system
            String domainQueryPackage = basePackage + "." + bussiPackage + ".vo";//com.qunar.pay.g2.fpp.system.vo


            //java,xml文件名称
            String sqlMapperPath = sqlmapPackage.replace(".", "/") + "/" + className + "Mapper.xml";
            String domainPath = domainPackage.replace(".", "/") + "/" + className + ".java";
            String mapperPath = mapperPackage.replace(".", "/") + "/" + className + "Mapper.java";
            String servicePath = servicePackage.replace(".", "/") + "/" + className + "Service.java";
            String serviceImplPath = serviceImplPackage.replace(".", "/") + "/" + className + "ServiceImpl.java";
            String controllerPath = controllerPackage.replace(".", "/") + "/" + className + "Controller.java";
            String domainQueryPath = domainQueryPackage.replace(".", "/") + "/" + className + "Vo.java";

            //测试相关的数据
            String pageIndexPath = controllerPackage.replace(".", "/") + "/" + lowerName + "Index.vm";
            String pageAddPath = controllerPackage.replace(".", "/") + "/" + lowerName + "AddDialog.vm";
            String pageEditPath = controllerPackage.replace(".", "/") + "/" + lowerName + "EditDialog.vm";
            String pageDetailPath = controllerPackage.replace(".", "/") + "/" + lowerName + "Detail.vm";


            String sqlMapperFlag = CodeResourceUtil.getConfigInfo("sqlmap_flag");
            String domainFlag = CodeResourceUtil.getConfigInfo("domain_flag");
            String mapperFlag = CodeResourceUtil.getConfigInfo("mapper_flag");
            String serviceFlag = CodeResourceUtil.getConfigInfo("service_flag");
            String serviceImplFlag = CodeResourceUtil.getConfigInfo("service_impl_flag");
            String controllerFlag = CodeResourceUtil.getConfigInfo("controller_flag");
            String domainQueryFlag = CodeResourceUtil.getConfigInfo("domain_query_flag");

            String checkFlag = CodeResourceUtil.getConfigInfo("check_flag");
            String repairFlag = CodeResourceUtil.getConfigInfo("repair_flag");
            String smapperFlag = CodeResourceUtil.getConfigInfo("smapper_flag");
            String ssqlFlag = CodeResourceUtil.getConfigInfo("ssql_flag");


            Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
            List<ColumnData> columnDatas = createBean.getColumnDatas(tableName);
            List<ColumnData> columnKeyDatas = createBean.getColumnKeyDatas(columnDatas);
            String columnKeyParam = createBean.getColumnKeyParam(columnKeyDatas);
            String columnKeyUseParam = createBean.getColumnKeyUseParam(columnKeyDatas);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = dateformat.format(new Date());
            System.out.println("时间:" + nowDate);

            Map<String, Object> root = new HashMap<String, Object>();
            root.put("author", author);
            root.put("className", className); //
            root.put("lowerName", lowerName);
            root.put("codeName", codeName);
            root.put("tableName", tableName);
            root.put("tableNameUpper", tableNameUpper);
            root.put("tablesAsName", tablesAsName);
            root.put("bussPackage", bussiPackage);
            root.put("domainPackage", domainPackage);
            root.put("domainQueryPackage", domainQueryPackage);
            root.put("mapperPackage", mapperPackage);
            root.put("servicePackage", servicePackage);
            root.put("serviceImplPackage", serviceImplPackage);
            root.put("controllerPackage", controllerPackage);
            root.put("keyType", keyType);
            root.put("nowDate", nowDate);
            /******************************生成bean字段*********************************/
            root.put("feilds", createBean.getBeanFeilds(tableName, className)); //生成bean
            root.put("queryfeilds", createBean.getBeanFeilds(tableName, className)); //生成bean

            /*******************************生成sql语句**********************************/
            root.put("columnDatas", columnDatas); //生成bean
            root.put("columnKeyDatas", columnKeyDatas); //生成bean
            root.put("columnKeyParam", columnKeyParam); //生成主键参数
            root.put("columnKeyUseParam", columnKeyUseParam); //生成使用的主键参数
            root.put("SQL", sqlMap);


            Configuration cfg = new Configuration();
            String templateBasePath = CodeGenerateFactory.getProjectPath() + CodeResourceUtil.getConfigInfo("templatepath_ftl");
            cfg.setDirectoryForTemplateLoading(new File(templateBasePath));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            if ("Y".equals(sqlMapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap.ftl", pathSrc, sqlMapperPath);
            }
            if ("Y".equals(domainFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "domainClass.ftl", pathSrc, domainPath);
            }
            if ("Y".equals(mapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "mapperClass.ftl", pathSrc, mapperPath);
            }
            if ("Y".equals(serviceFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "serviceClass.ftl", pathSrc, servicePath);
            }
            if ("Y".equals(serviceImplFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "serviceImplClass.ftl", pathSrc, serviceImplPath);
            }
            if ("Y".equals(controllerFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "controllerClass.ftl", pathSrc, controllerPath);
                FreemarkerEngine.createFileByFTL(cfg, root, "pageIndex.ftl", pathSrc, pageIndexPath);
                FreemarkerEngine.createFileByFTL(cfg, root, "pageAdd.ftl", pathSrc, pageAddPath);
                FreemarkerEngine.createFileByFTL(cfg, root, "pageDetail.ftl", pathSrc, pageDetailPath);
                FreemarkerEngine.createFileByFTL(cfg, root, "pageEdit.ftl", pathSrc, pageEditPath);
            }
            if ("Y".equals(checkFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "checkserviceClass.ftl", pathSrc,  "/checker/" + className + "Checker.java");
            }
            if ("Y".equals(repairFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "repairserviceClass.ftl", pathSrc,  "/repair/" + className + "Repair.java");
            }
            if ("Y".equals(smapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "SmapperClass.ftl", pathSrc, "/smapper"+ "/S" + className + "Mapper.java");
            }
            if ("Y".equals(ssqlFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap_sharding.ftl", pathSrc, "/mapper" + "/S" + className + "Mapper.xml");
            }
            log.info("----------------------------代码生成完毕---------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 获取项目的路径
     *
     * @return
     */
    public static String getProjectPath() {
        String path = System.getProperty("user.dir").replace("/", "/") + "/";
        return path;
    }
}
