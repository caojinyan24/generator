package executor;

import codeGenerate.vo.FtlDef;
import codeGenerate.service.CodeGenerateFactory;

/**
 * 描述：根据表生成
 * @author：zhoujf
 * @since：
 * @version:1.0
 */
public class CodeUtil {

	public static void main(String[] args) {
		 /** 此处修改成你的 表名 和 中文注释***/
		 String tableName="abc";
		 String codeName ="abc";


		 CodeGenerateFactory.codeGenerateByFTL(tableName, codeName, FtlDef.KEY_TYPE_02);
	}
}
