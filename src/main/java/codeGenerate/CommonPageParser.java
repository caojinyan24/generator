package codeGenerate;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.factory.CodeGenerateFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>zhoujf<br>
 * <b>日期：</b> 2013-2-02 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class CommonPageParser {
    private static final Logger logger = LoggerFactory.getLogger(CommonPageParser.class);
    private static VelocityEngine ve;// = VelocityEngineUtil.getVelocityEngine();
    private final static String CONTENT_ENCODING = "UTF-8";


    private static boolean isReplace = true;  //是否可以替换文件 true =可以替换，false =不可以替换


    static {
        try {
            //获取文件模板根路径
            String templateBasePath = CodeGenerateFactory.getProjectPath() + CodeResourceUtil.getConfigInfo("templatepath_vm");//"Constant.WORK_TEMPLATE_PATH;
            Properties properties = new Properties();
            properties.setProperty(Velocity.RESOURCE_LOADER, "file");
            properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templateBasePath);
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
            properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
            properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
            properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
            properties.setProperty("directive.set.null.allowed", "true");
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            ve = velocityEngine;
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * <br>
     * <b>功能：</b>生成页面文件<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-7-23 <br>
     *
     * @param context      内容上下文
     * @param templateName 模板文件路径（相对路径）article\\article_main.html
     */
    public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {
        try {
            String workspasePath = CodeResourceUtil.getConfigInfo("workspace_path");
            File file = new File(workspasePath + File.separator + fileDirPath + File.separator + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else {
                if (isReplace) {
                    logger.info("替换文件:" + file.getAbsolutePath());
                } else {
                }
            }

            Template template = ve.getTemplate(templateName, CONTENT_ENCODING);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
            logger.info("生成文件：" + file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
