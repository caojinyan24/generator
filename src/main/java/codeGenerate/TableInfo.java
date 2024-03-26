package codeGenerate;

public class TableInfo {
	
	//表名
	private String tableName ;
	
	//表注释
	private String tableComment;

	public TableInfo(String tableName, String tableComment) {
		this.tableName = tableName;
		this.tableComment = tableComment;
	}

	public TableInfo() {
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}



}
