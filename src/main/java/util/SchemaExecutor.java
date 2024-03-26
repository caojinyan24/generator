package util;

import codeGenerate.CreateBean;
import codeGenerate.def.CodeResourceUtil;

import java.io.BufferedReader;
import java.io.File;

public class SchemaExecutor {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) {
        new SchemaExecutor().convertToShardingAndExecute();


    }

    public void convertToShardingAndExecute() {
        CreateBean createBean = new CreateBean();
        System.out.println("url=" + url);
        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        File[] files = new File(new File("").getAbsolutePath() + "/src/main/resources/dbstructure").listFiles();
        for (File item : files) {
            try {
                if (!item.isFile()||!item.getName().startsWith("auth_")) {
                    continue;
                }
                if (!item.isFile()) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new java.io.FileReader(item.getAbsolutePath()));
                while (in.ready()) {
                    sb.append(in.readLine()).append("\n");
                }

                String schema = sb.toString();
                schema = schema.replace("_{0,255}", "");
//                schema= schema.replace(";",";\n");

//                System.out.println("***************");

//                System.out.println(schema);
                System.out.println("***************");
                String[] sch = schema.split(";");
                for (int i = 0; i < sch.length; i++) {
                    if (sch[i].trim().length() == 0) {
                        continue;
                    }
                    try {
                        createBean.getConnection().prepareCall(sch[i]).execute();
                    } catch (Exception e) {
                        if (!e.toString().contains("already exists")) {
                            System.out.println("Err:" + sch[i]);
                            System.out.println(e);
                        }

                    }
                }

            } catch (Exception e) {
                System.out.println("ERROR:" + item.getName());
                System.out.println(e);


            }
        }
    }

    public void execute() {
        CreateBean createBean = new CreateBean();
        System.out.println("url=" + url);
        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        File[] files = new File(new File("").getAbsolutePath() + "/src/main/resources/dbstructure").listFiles();
        for (File item : files) {
            try {
                if (!item.isFile()) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new java.io.FileReader(item.getAbsolutePath()));
                while (in.ready()) {
                    sb.append(in.readLine()).append("\n");
                    System.out.println(sb.toString());
                }
                try {
                    createBean.getConnection().prepareCall(String.format("drop table if exists %s", item.getName())).execute();

                    createBean.getConnection().prepareCall(sb.toString()).execute();
                } catch (Exception e) {
                    System.out.println(e);


                }


            } catch (Exception e) {
                System.out.println("ERROR:" + item.getName());
                System.out.println(e);


            }
        }
    }
}

