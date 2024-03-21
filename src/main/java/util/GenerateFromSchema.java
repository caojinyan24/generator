package util;

import codeGenerate.CreateBean;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.task.CodeGenerateTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 根据建表语句的文件，生成代码
 */
public class GenerateFromSchema {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws SQLException, IOException {
        CreateBean createBean = new CreateBean();
        System.out.println("url=" + url);
        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        File[] files = new File(new File("").getAbsolutePath() + "/src/main/resources/dbstructure").listFiles();
        for (File item : files) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new java.io.FileReader(item.getAbsolutePath()));
                while (in.ready()) {
                    sb.append(in.readLine());
                }
                createBean.getConnection().prepareCall(sb.toString()).execute();
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        List<TableInfo> tables = createBean.getTablesInfo();
        for (TableInfo info : tables) {
            System.out.println(info.getTableName() + ";" + info.getTableComment());
        }
        batchGenerateCode(tables);
    }


    public static void batchGenerateCode(List<TableInfo> tables) {
        try {
            @SuppressWarnings({"unchecked", "rawtypes"})
            ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue());
            List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(9000);
            long start = new Date().getTime();
            for (TableInfo tableInfo : tables) {
                futures.add(executor.submit(new CodeGenerateTask(tableInfo)));
            }
            // 提交所有的任务后,关闭executor
            System.out.println("Starting shutdown...");
            executor.shutdown();

            // 每秒钟打印执行进度
            while (!executor.isTerminated()) {
                executor.awaitTermination(1, TimeUnit.SECONDS);
                int progress = Math.round((executor.getCompletedTaskCount() * 100) / executor.getTaskCount());
                System.out.println(new Date() + ":" + progress + "% done (" + executor.getCompletedTaskCount() + " the files have been created).");
            }
            int errorCount = 0;
            int successCount = 0;
            for (Future<Boolean> future : futures) {
                if (future.get()) {
                    successCount++;
                } else {
                    errorCount++;
                }
            }
            long end = new Date().getTime();
            System.out.println(successCount + " the files were successfully created, but " +
                    errorCount + " failed.");
            System.out.println(" 用时： " + (end - start) + "ms");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
