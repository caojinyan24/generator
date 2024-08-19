package executor;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.service.CreateBean;

import java.io.BufferedReader;
import java.io.File;

public class SchemaExecutor {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws Exception {
        new SchemaExecutor().execute();

    }

    public void convertToShardingAndExecute(String group) {
        CreateBean createBean = new CreateBean();
        System.out.println("url=" + url);
        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        File[] files = new File(new File("").getAbsolutePath() + "/src/main/resources/dbstructure").listFiles();
        for (File item : files) {
            try {
                if (!item.isFile() || !item.getName().startsWith(group)) {
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
                schema = schema.replace("_{0,255}", "_0");
                schema = schema.replace(";", ";\n");

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
                        System.out.println("execute " + sch[i]);
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

    public void execute() throws Exception {
        CreateBean createBean = new CreateBean();
//        System.out.println("url=" + url);
//        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        File[] files = new File(new File("").getAbsolutePath() + "/src/main/resources/dbstructure").listFiles();
        for (File item : files) {
            if (item.isFile()&&item.getName().endsWith(".sql")) {
                execute(item);
            }
        }
    }

    public void execute(File file) throws Exception {

        CreateBean createBean = new CreateBean();
//        System.out.println("url=" + url);
//        System.out.println("username=" + username);
        createBean.setMysqlInfo(url, username, passWord);
        System.out.println(new File("").getAbsolutePath());
        try {
            BufferedReader in = new BufferedReader(new java.io.FileReader(file.getAbsolutePath()));
            String insert = "";
            while (in.ready()) {
                String sql = in.readLine();
                insert = insert + sql;
                if(sql.equals("\n")||sql.trim().length()==0){
                    System.out.println("====================================execute：" + insert + "====================================");
                    createBean.getConnection().prepareCall(insert).execute();
                    insert="";
                }

//                if (sql.contains(";")) {

//                    insert = "";
//                }

            }



        } catch (Exception e) {
            System.out.println(e);


        }
    }
}



