package util;

import codeGenerate.CreateBean;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.task.CodeGenerateTask;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述：根据数据库所有的表的生成代码
 *
 * @author：zhoujf
 * @version:1.0
 * @since
 */
public class JDCodeTool {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws SQLException {
        CreateBean createBean = new CreateBean();
        createBean.setMysqlInfo(url, username, passWord);
        List<TableInfo> tables = createBean.getTablesInfo();
        for (TableInfo info : tables) {
                List<TableInfo>in=new ArrayList<TableInfo>();
                in.add(info);
                batchGenerateCode(in);
            System.out.println(info.getTableName() + ";" + info.getTableComment());
        }
//        batchGenerateCode(tables);
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
