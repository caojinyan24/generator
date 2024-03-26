package codeGenerate.factory;

import codeGenerate.ColumnData;
import codeGenerate.CreateBean;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FreemarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.apache.commons.lang.StringUtils;
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


//            Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
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
//            root.put("SQL", sqlMap);

/*******************************解析index**********************************/
//            String generateByUniqKeyForShardingDB = createBean.generateByUniqKeyForShardingDB(tableName);
//            root.put("generateByUniqKeyForShardingDB", generateByUniqKeyForShardingDB);
String uniqItemParam=createBean.getParameterUniq(tableName);
            root.put("uniqItemParam", uniqItemParam);

            /*******************************解析index**********************************/

            Configuration cfg = new Configuration();
            String templateBasePath = CodeGenerateFactory.getProjectPath() + CodeResourceUtil.getConfigInfo("templatepath_ftl");
            cfg.setDirectoryForTemplateLoading(new File(templateBasePath));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            if ("Y".equals(sqlMapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap_core.ftl", pathSrc, sqlMapperPath);
            }
            if ("Y".equals(domainFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "domainClass.ftl", pathSrc, domainPath);
            }
            if ("Y".equals(mapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "mapperclass_core.ftl", pathSrc, mapperPath);
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
                FreemarkerEngine.createFileByFTL(cfg, root, "checkserviceClass-core.ftl", pathSrc, "/checker/core/" + className + "Checker.java");
            }
            if ("Y".equals(repairFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "repairserviceClass-core.ftl", pathSrc, "/repair/core/" + className + "Repair.java");
            }
            if ("Y".equals(smapperFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "mapperClass-Sharding.ftl", pathSrc, "/smapper" + "/S" + className + "Mapper.java");
            }
            if ("Y".equals(ssqlFlag)) {
                FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap_sharding.ftl", pathSrc, "/smapperxml" + "/S" + className + "Mapper.xml");
            }
            if("Y".equals(CodeResourceUtil.getConfigInfo("sqlmap_core"))){
                FreemarkerEngine.createFileByFTL(cfg, root, "sqlmap_core.ftl", pathSrc, "/sqlmap_core/" +   className + "Mapper.xml");
            }
            if("Y".equals(CodeResourceUtil.getConfigInfo("mapperclass_core"))){
                FreemarkerEngine.createFileByFTL(cfg, root, "mapperclass_core.ftl", pathSrc, "/mapperclass_core/" +   className + "Mapper.java");
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
