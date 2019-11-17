package util;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;
import org.apache.commons.lang.StringUtils;

/**
 * 描述：根据自定义表生成
 * @author：zhoujf
 * @since：
 * @version:1.0
 */
public class JDCodeToolFree {

	public static void main(String[] args) {
		 /** 此处修改成你的 表名 和 中文注释***/
		 String codeCgTables = CodeResourceUtil.getConfigInfo("wu_transaction_info");
		 if(StringUtils.isEmpty(codeCgTables)){
			 return;
		 }
		 String[] tables =codeCgTables.split(",");
		 for(String tableName:tables){
			CodeGenerateFactory.codeGenerateByFTL(tableName, "", FtlDef.KEY_TYPE_02);
		 }
	}
}
