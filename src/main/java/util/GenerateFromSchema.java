package util;

import codeGenerate.CreateBean;
import codeGenerate.Generator;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
        Generator.batchGenerateCode(tables);
    }


}
