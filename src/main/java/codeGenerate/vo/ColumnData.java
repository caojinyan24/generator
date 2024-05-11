package codeGenerate.vo;

/**
 * 表字段类
 * @author zhoujf
 *
 */
public class ColumnData {

	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	
	
	
	private String columnName;
	
	//表字段对应的实体属性名
	private String domainPropertyName;

	/**[实体：数据类型]*/
	private String dataType;
	private String columnComment;
	
	/**[表字段：数据类型]*/
	private String columnType;
	
	/**[表字段：主键类型]*/
	private String columnKey;
	
	/**字符串允许输入最大长度*/
	private String charmaxLength = "";
	/**是否允许为空 Y/N*/
	private String nullable;
	/**小数点*/
	private String scale;
	/**精度-长度*/
	private String precision;
	
	
	
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getScale() {
		return scale;
	}
	public String getPrecision() {
		return precision;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getCharmaxLength() {
		return charmaxLength;
	}
	public String getNullable() {
		return nullable;
	}
	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getDomainPropertyName() {
		return domainPropertyName;
	}
	public void setDomainPropertyName(String domainPropertyName) {
		this.domainPropertyName = domainPropertyName;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	@Override
	public String toString() {
		return "ColumnData{" +
				"columnName='" + columnName + '\'' +
				", domainPropertyName='" + domainPropertyName + '\'' +
				", dataType='" + dataType + '\'' +
				", columnComment='" + columnComment + '\'' +
				", columnType='" + columnType + '\'' +
				", columnKey='" + columnKey + '\'' +
				", charmaxLength='" + charmaxLength + '\'' +
				", nullable='" + nullable + '\'' +
				", scale='" + scale + '\'' +
				", precision='" + precision + '\'' +
				'}';
	}
}
