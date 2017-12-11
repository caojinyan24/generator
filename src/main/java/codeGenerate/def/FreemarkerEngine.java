package codeGenerate.def;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FreemarkerEngine {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerEngine.class);

    public static void createFileByFTL(Configuration cfg,
                                       Map<String, Object> root, String ftlName, String fileDirPath, String targetFile) {
        logger.info("createFileByFTL--begin:{}", ftlName);
        String encoding = CodeResourceUtil.getSYSTEM_ENCODING();
        String workspasePath = CodeResourceUtil.getConfigInfo("workspace_path");
        boolean isReplace = new Boolean(CodeResourceUtil.getConfigInfo("isReplace"));
        try {
            File file = new File(workspasePath + File.separator + fileDirPath + File.separator + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else {
                if (isReplace) {
                    logger.info("替换文件:" + file.getAbsolutePath());
                } else {
                }
            }
            FileOutputStream os = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(os, encoding);
            Template temp = cfg.getTemplate(ftlName, encoding);
            temp.process(root, out);
            out.flush();
        } catch (IOException e) {
            logger.error("createFileByFTL-error:", e);
        } catch (TemplateException e) {
            logger.error("createFileByFTL-error:", e);
        } catch (Exception e) {
            logger.info("createFileByFTL-error:", e);
        }
    }
}
