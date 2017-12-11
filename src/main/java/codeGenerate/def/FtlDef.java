package codeGenerate.def;

public final class FtlDef {
	private FtlDef() {
	}
	/**默认必须输入字段个数：参数*/
	public static String FIELD_REQUIRED_NAME ="field_required_num";
	/**默认一行显示几个字段：参数*/
	public static String FIELD_ROW_NAME ="field_row_num";
	
	/**主键字段*/
	public static String CG_TABLE_ID = "cg_table_id";
	/**生成几个查询条件*/
	public static String SEARCH_FIELD_NUM = "search_field_num";
	
	
	
	
	/**
	 * 主键生成类型
	 * 01：采用UUID
	 * 02：采用主键自增
	 */
	public static String KEY_TYPE_01 = "01";
	public static String KEY_TYPE_02 = "02";
	
	
	
}
