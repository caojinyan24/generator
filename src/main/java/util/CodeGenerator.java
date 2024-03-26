package util;

import codeGenerate.CreateBean;
import codeGenerate.Generator;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：根据数据库所有的表的生成代码
 *
 * @author：zhoujf
 * @version:1.0
 * @since
 */
public class CodeGenerator {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws Exception {

        System.out.println(new File("").getAbsolutePath());

        List<TableInfo> tables = new ArrayList<TableInfo>();

        final File file = new File("out/tables.txt");
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath()));
        while (in.ready()) {
            String line = in.readLine();
            String[] info = line.split(";");
            if(info.length<2){
                tables.add(new TableInfo(info[0], info[0]));
            }else{
                tables.add(new TableInfo(info[0], info[1]));

            }
        }
        Generator.batchGenerateCode(tables);

    }


}
