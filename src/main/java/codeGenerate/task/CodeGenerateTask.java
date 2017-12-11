package codeGenerate.task;

import java.util.concurrent.Callable;

import codeGenerate.TableInfo;
import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;

public class CodeGenerateTask implements Callable<Boolean>{
	
	private TableInfo tableInfo;

	public CodeGenerateTask(TableInfo tableInfo){
		this.tableInfo = tableInfo;
	}
	public Boolean call() throws Exception {
		boolean flag = false;
		try {
			CodeGenerateFactory.codeGenerateByFTL(tableInfo.getTableName(), tableInfo.getTableComment(),FtlDef.KEY_TYPE_02);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
