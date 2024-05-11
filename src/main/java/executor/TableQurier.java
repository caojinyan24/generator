package executor;

import codeGenerate.service.CreateBean;
import codeGenerate.vo.TableInfo;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.ToolUtil;

import java.io.File;
import java.util.List;

/**
 * 查询配置的数据库中所有表名，输出到tables文件
 */
public class TableQurier {

    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws Exception {
        final File file = new File("out/tables.txt");
        System.out.println(file.getAbsoluteFile());
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        CreateBean createBean = new CreateBean();
        createBean.setMysqlInfo(url, username, passWord);
        List<TableInfo> tables = createBean.getTablesInfo();
        for (TableInfo info : tables) {
            ToolUtil.writeFile(file, info.getTableName().toLowerCase() + ";" + (info.getTableComment().trim().length() == 0 ? info.getTableName().toLowerCase() : info.getTableComment()));
        }
    }

}
