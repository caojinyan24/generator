package util;

import codeGenerate.CreateBean;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.factory.ToolUtil;

import java.io.File;
import java.util.List;

/**
 * 查询配置的数据库中所有表名，输出到tables文件
 */
public class QueryTable {
	
	private static String url= CodeResourceUtil.URL;
	private static String username =  CodeResourceUtil.USERNAME;
	private static String passWord = CodeResourceUtil.PASSWORD;
	
	public static void main(String[] args) throws Exception {
		final File file = new File("tables.txt");
		if(!file.exists()) {
			file.createNewFile();
		}else{
			file.delete();
			file.createNewFile();
		}
		CreateBean createBean=new CreateBean();
		createBean.setMysqlInfo(url, username, passWord);
		List<TableInfo> tables = createBean.getTablesInfo();
		for(TableInfo info:tables){
			System.out.println(info.getTableName()+";"+info.getTableComment());
			ToolUtil.writeFile(file, info.getTableName().toLowerCase()+";"+info.getTableComment());
		}
	}

}
