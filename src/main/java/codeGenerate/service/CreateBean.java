package codeGenerate.service;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.TableConvert;
import codeGenerate.vo.ColumnData;
import codeGenerate.vo.IndexData;
import codeGenerate.vo.TableInfo;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>zhoujf<br>
 * <b>日期：</b> 2013-12-16 <br>
 * <b>版权所有：<b>版权所有(C) 2013， <br>
 */
public class CreateBean {
    private Connection connection = null;
    static String url;
    static String username;
    static String password;
    static String rt = "\r\t";
    String SQLTables = "show tables";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("static-access")
    public void setMysqlInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<String> getTables() throws SQLException {


        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(SQLTables);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<String>();
        while (rs.next()) {
            String tableName = rs.getString(1).toUpperCase();
            list.add(tableName);
        }
        rs.close();
        ps.close();
        con.close();
        return list;
    }

    public List<TableInfo> getTablesInfo() throws SQLException {
        String SQLTables = "select t.TABLE_NAME ,t.TABLE_COMMENT from information_schema.tables  t  WHERE table_schema = '" + CodeResourceUtil.getDATABASE_NAME() + "'";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(SQLTables);
        ResultSet rs = ps.executeQuery();
        List<TableInfo> list = new ArrayList<TableInfo>();
        while (rs.next()) {
            TableInfo tableInfo = new TableInfo();
            String tableName = rs.getString(1);
            String tableComment = rs.getString(2);
            tableInfo.setTableName(tableName);
            tableInfo.setTableComment(tableComment);
            list.add(tableInfo);
        }
        rs.close();
        ps.close();
        con.close();
        return list;
    }

    public Map<String, String> getTableCommentMap() throws SQLException {
        String SQLTables = "select t.TABLE_NAME ,t.TABLE_COMMENT from information_schema.tables  t  WHERE table_schema = '" + CodeResourceUtil.getDATABASE_NAME() + "'";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(SQLTables);
        ResultSet rs = ps.executeQuery();
        Map<String, String> map = new HashMap<String, String>();
        while (rs.next()) {
            String tableName = rs.getString(1).toUpperCase();
            String tableComment = rs.getString(2);
            map.put(tableName, tableComment);
        }
        rs.close();
        ps.close();
        con.close();
        return map;
    }

    /**
     * 查询表的字段，封装成List
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
        String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable,COLUMN_KEY from information_schema.columns where table_name =  '" + tableName + "' " + "and table_schema =  '" + CodeResourceUtil.DATABASE_NAME + "' order by column_name";

        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(SQLColumns);
        List<ColumnData> columnList = new ArrayList<ColumnData>();
        ResultSet rs = ps.executeQuery();
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        int i = 0;
        while (rs.next()) {
            String name = rs.getString(1);
            String type = rs.getString(2);
            String comment = rs.getString(3);
            String precision = rs.getString(4);
            String scale = rs.getString(5);
            String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
            String nullable = TableConvert.getNullAble(rs.getString(7));
            String columnKey = rs.getString(8) == null ? "" : rs.getString(8);
            type = this.getType(type, precision, scale);
            String domainPropertyName = getcolumnNameToDomainPropertyName(name);


            ColumnData cd = new ColumnData();
            cd.setColumnName(name);
            cd.setDataType(type);
            cd.setColumnType(rs.getString(2));
            cd.setColumnComment(comment);
            cd.setPrecision(precision);
            cd.setScale(scale);
            cd.setCharmaxLength(charmaxLength);
            cd.setNullable(nullable);
            cd.setDomainPropertyName(domainPropertyName);
            cd.setColumnKey(columnKey);
            columnList.add(cd);
            i++;
        }
        argv = str.toString();
        method = getset.toString();
        rs.close();
        ps.close();
        con.close();
        return columnList;
    }

    /**
     * 查询表的字段，封装成List
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<IndexData> getIndexDatas(String tableName) throws SQLException {
        String SQLColumns = String.format("SELECT  INDEX_NAME , SEQ_IN_INDEX,COLUMN_NAME,NON_UNIQUE FROM information_schema.STATISTICS where TABLE_NAME='%s' and TABLE_SCHEMA='%s'", tableName, CodeResourceUtil.getDATABASE_NAME());
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(SQLColumns);
        ResultSet rs = ps.executeQuery();
        Map<String, IndexData> indexMap = new HashMap<String, IndexData>();
        while (rs.next()) {
            String indexName = rs.getString(1);
            Integer indexSeq = rs.getInt(2);
            String column = rs.getString(3);
            Integer nonUniq = rs.getInt(4);
            if (indexMap.get(indexName) == null) {
                List<String> columns = initCapacity(indexSeq);
                columns.set(indexSeq - 1, column);
                indexMap.put(indexName, new IndexData(columns, nonUniq, indexName));
            } else {
                IndexData indexData = indexMap.get(indexName);
                if (indexData.indexFields.size() < indexSeq) {
                    indexData.indexFields.addAll(initCapacity(indexSeq - indexData.indexFields.size()));
                }
                indexData.indexFields.set(indexSeq - 1, column);
                indexMap.put(indexName, indexData);
            }
        }
        rs.close();
        ps.close();
        con.close();
        return new ArrayList<IndexData>(indexMap.values());
    }

    private List<String> initCapacity(Integer c) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < c; i++) {
            result.add(new String());
        }
        return result;
    }

    public List<ColumnData> getColumnKeyDatas(List<ColumnData> columnList) {
        List<ColumnData> columnKeyList = new ArrayList<ColumnData>();
        if (columnList != null && columnList.size() > 0) {
            for (ColumnData column : columnList) {
                if ("PRI".equals(column.getColumnKey())) {
                    columnKeyList.add(column);
                }
            }
        }
        return columnKeyList;
    }

    /**
     * @param @param  columnList 主键column
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getKeyParam
     * @Description: 组装主键参数
     */
    public String getColumnKeyParam(List<ColumnData> columnList) {
        StringBuffer sb = new StringBuffer("");
        if (columnList != null && columnList.size() > 0) {
            for (ColumnData column : columnList) {
                sb.append(column.getDataType()).append(" ").append(column.getDomainPropertyName()).append(",");
            }
        }
        String str = sb.toString();
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * @param @param  columnList 主键column
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getKeyParam
     * @Description: 组装使用的主键参数
     */
    public String getColumnKeyUseParam(List<ColumnData> columnList) {
        StringBuffer sb = new StringBuffer("");
        if (columnList != null && columnList.size() > 0) {
            for (ColumnData column : columnList) {
                sb.append(column.getDomainPropertyName()).append(",");
            }
        }
        String str = sb.toString();
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private String method;
    private String argv;

    /**
     * 生成实体Bean 的属性和set,get方法
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public String getBeanFeilds(String tableName, String className) throws SQLException {
        List<ColumnData> dataList = getColumnDatas(tableName);
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        StringBuffer toString = new StringBuffer("\r\t\r\t@Override\n" +
                "    public String toString() {\n" +
                "        final StringBuilder sb = new StringBuilder(\"");
        toString.append(className).append("{\"").append(");\r\t");
        String equalMethodReturnFields = "";
        String hashCodeFields = "";
        for (int i = 0; i < dataList.size(); i++) {
            ColumnData d = dataList.get(i);
            String name = d.getDomainPropertyName();
            String type = d.getDataType();
            String comment = d.getColumnComment();
            String maxChar = name.substring(0, 1).toUpperCase();
            str.append("\r\t").append("/**");
            str.append("\r\t").append(" * ").append(comment);
            str.append("\r\t").append(" */");
            str.append("\r\t").append("private ").append(type + " ").append(name).append(";");
            if (!(name.equals("pid") || name.equals("createTime") || name.equals("updateTime") || name.equals("jpaVersion"))) {
                if (equalMethodReturnFields.length() == 0) {
                    equalMethodReturnFields = equalMethodReturnFields.concat(String.format("Objects.equals(%s, that.%s)", name, name));
                } else {
                    equalMethodReturnFields = equalMethodReturnFields.concat(String.format(" && Objects.equals(%s, that.%s)", name, name));
                }
                if (hashCodeFields.length() == 0) {
                    hashCodeFields = hashCodeFields.concat(name);
                } else {
                    hashCodeFields = hashCodeFields.concat(String.format(",%s", name));

                }
            }

            String method = maxChar + name.substring(1, name.length());
            getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
            getset.append("    return this.").append(name).append(";\r\t}");
            getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
            getset.append("    this." + name + "=").append(name).append(";\r\t}");

            toString.append("    sb.append(\", ").append(d.getDomainPropertyName()).append("=\").append(").append(d.getDomainPropertyName()).append(");\r\t");


        }
        argv = str.toString();
        method = getset.toString();
        toString.append("    sb.append('}');\n" +
                "\t\treturn sb.toString();\n}");
        //生成equal和hashcode方法


        //生成自定义方法
        String getUniqKeyValue = String.format("   public String getUniqKeyValue() {\n" +
                "        return %s;\n" +
                "    }\n", getUniq(tableName));

        String equalFormat = String.format("    @Override\n" +
                "    public boolean equals(Object o) {\n" +
                "        if (this == o) return true;\n" +
                "        if (!(o instanceof %s)) return false;\n" +
                "        %s that = (%s) o;\n" +
                "return %s ;" +
                "}\n", className, className, className, equalMethodReturnFields);
        String hashCode = String.format("    @Override\n" +
                "    public int hashCode() {\n" +
                "        return Objects.hash(%s);}", hashCodeFields);
        return getUniqKeyValue + argv + method + toString + equalFormat + hashCode;
    }

    public String getUniq(String tableName) throws SQLException {
        String result = "";
        List<IndexData> indexDataList = getIndexDatas(tableName);
        for (IndexData item : indexDataList) {
            if (item.getIndexType() == 0 && !"PRIMARY".equals(item.keyName)) {
                for (String columnName : item.getIndexFields()) {
                    result += "+" + getcolumnNameToDomainPropertyName(columnName);
                }
                break;
            }
        }
        if (result.startsWith("+")) {
            result = result.substring(1);
        }
        if (result.length() == 0) {
            System.out.println("Err:noUniqKey");
            return "\"Err:noUniqKey\"";
        }
        return result;
    }

    /**
     * 获取字段对应的数据类型
     *
     * @param dataType
     * @param precision
     * @param scale
     * @return
     */
    public String getType(String dataType, String precision, String scale) {
        dataType = dataType.toLowerCase();
        if (dataType.contains("char")) {
            dataType = "String";
        } else if (dataType.contains("text")) {
            dataType = "String";
        } else if (dataType.contains("bigint")) {
            dataType = "Long";
        } else if (dataType.contains("int")) {
            dataType = "Integer";
        } else if (dataType.contains("float")) {
            dataType = "Float";
        } else if (dataType.contains("double")) {
            dataType = "Double";
        } else if (dataType.contains("number")) {
            if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
                dataType = "BigDecimal";
            } else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 6) {
                dataType = "Long";
            } else {
                dataType = "Integer";
            }
        } else if (dataType.contains("decimal")) {
            dataType = "BigDecimal";
        } else if (dataType.contains("date")) {
            dataType = "Date";
        } else if (dataType.contains("time")) {
            dataType = "Date";
        } else if (dataType.contains("clob")) {
            dataType = "Clob";
        } else {
            dataType = "Object";
        }
        return dataType;
    }

    public void getPackage(int type, String createPath, String content, String packageName, String className, String extendsClassName, String... importName) throws Exception {
        if (null == packageName) {
            packageName = "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(packageName).append(";\r");
        sb.append("\r");
        for (int i = 0; i < importName.length; i++) {
            sb.append("import ").append(importName[i]).append(";\r");
        }
        sb.append("\r");
        sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
        sb.append("\r");
        sb.append("\rpublic class ").append(className);
        if (null != extendsClassName) {
            sb.append(" extends ").append(extendsClassName);
        }
        if (type == 1) { //bean
            sb.append(" ").append("implements java.io.Serializable {\r");
        } else {
            sb.append(" {\r");
        }
        sb.append("\r\t");
        sb.append("private static final long serialVersionUID = 1L;\r\t");
        String temp = className.substring(0, 1).toLowerCase();
        temp += className.substring(1, className.length());
        if (type == 1) {
            sb.append("private " + className + " " + temp + "; // entity ");
        }
        sb.append(content);
        sb.append("\r}");
        System.out.println(sb.toString());
        this.createFile(createPath, "", sb.toString());
    }

    /**
     * <br>
     * <b>功能：</b>表名转换成类名 每_首字母大写<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param tableName
     * @return
     */
    public String getTablesNameToClassName(String tableName) {
        tableName = tableName.toLowerCase();
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(tempTableName);
            }
            // System.out.println(sb.toString());
            return sb.toString();
        } else {
            String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
            return tempTables;
        }
    }

    /**
     * <br>
     * <b>功能：</b>获取table别名<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param tableName
     * @return
     */
    public String getTablesASName(String tableName) {
        tableName = tableName.toLowerCase();
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = split[i].substring(0, 1).toLowerCase();
                sb.append(tempTableName);
            }
            // System.out.println(sb.toString());
            return sb.toString();
        } else {
            String tempTables = split[0].substring(0, 1).toLowerCase();
            return tempTables;
        }
    }

    /**
     * <br>
     * <b>功能：</b>表字段名转换成类属性名 每_首字母小写<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param columnName
     * @return
     */
    public String getcolumnNameToDomainPropertyName(String columnName) {
        columnName = columnName.toLowerCase();
        String[] split = columnName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(tempTableName);
            }
            // System.out.println(sb.toString());
            columnName = sb.toString();
            columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1, columnName.length());
            return columnName;
        } else {
            columnName = split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
            return columnName;
        }
    }

    /**
     * <br>
     * <b>功能：</b>创建文件<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param path
     * @param fileName
     * @param str
     * @throws IOException
     */
    public void createFile(String path, String fileName, String str) throws IOException {
        FileWriter writer = new FileWriter(new File(path + fileName));
        writer.write(new String(str.getBytes("utf-8")));
        writer.flush();
        writer.close();
    }

    /**
     * <br>
     * <b>功能：</b>生成sql语句<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param tableName
     * @throws Exception
     */
    static String selectStr = "select ";
    static String from = " from ";

    public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {
        Map<String, Object> sqlMap = new HashMap<String, Object>();
        List<ColumnData> columnDatas = getColumnDatas(tableName);
        String columns = this.getColumnSplit(columnDatas);
        String[] columnList = getColumnList(columns);  //表所有字段
        String columnFields = getColumnFields(columns); //表所有字段 按","隔开
        String insert = "INSERT INTO " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{" + columns.replaceAll("\\|", "},#{") + "})";
        String update = getUpdateSql(tableName, columnList);
        String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
        String selectById = getSelectByIdSql(tableName, columnList);
        String delete = getDeleteSql(tableName, columnList);
        sqlMap.put("columnList", columnList);
        sqlMap.put("columnFields", columnFields);
        sqlMap.put("insert", insert);
        sqlMap.put("update", update);
        sqlMap.put("delete", delete);
        sqlMap.put("updateSelective", updateSelective);
        sqlMap.put("selectById", selectById);
        return sqlMap;
    }

    /**
     * delete
     *
     * @param tableName
     * @param columnsList
     * @return
     * @throws SQLException
     */
    public String getDeleteSql(String tableName, String[] columnsList) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("delete ");
        sb.append("\t from ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
        return sb.toString();
    }

    /**
     * 根据id查询
     *
     * @param tableName
     * @param columnsList
     * @return
     * @throws SQLException
     */
    public String getSelectByIdSql(String tableName, String[] columnsList) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("select <include refid=\"Base_Column_List\" /> \n");
        sb.append("\t from ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
        return sb.toString();
    }

    /**
     * 获取所有的字段，并按","分割
     *
     * @param columns
     * @return
     * @throws SQLException
     */
    public String getColumnFields(String columns) throws SQLException {
        String fields = columns;
        if (fields != null && !"".equals(fields)) {
            fields = fields.replaceAll("[|]", ",");
        }
        return fields;
    }

    /**
     * @param columns
     * @return
     * @throws SQLException
     */
    public String[] getColumnList(String columns) throws SQLException {
        String[] columnList = columns.split("[|]");
        return columnList;
    }

    /**
     * 修改记录
     *
     * @param tableName
     * @param columnsList
     * @return
     * @throws SQLException
     */
    public String getUpdateSql(String tableName, String[] columnsList) throws SQLException {
        StringBuffer sb = new StringBuffer();

        for (int i = 1; i < columnsList.length; i++) {
            String column = columnsList[i];
            if ("CREATETIME".equals(column.toUpperCase()))
                continue;

            if ("UPDATETIME".equals(column.toUpperCase()))
                sb.append(column + "=now()");
            else
                sb.append(column + "=#{" + column + "}");
            //最后一个字段不需要","
            if ((i + 1) < columnsList.length) {
                sb.append(",");
            }
        }
        String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{" + columnsList[0] + "}";
        return update;
    }

    public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
        StringBuffer sb = new StringBuffer();
        ColumnData cd = columnList.get(0); //获取第一条记录，主键
        sb.append("\t<trim  suffixOverrides=\",\" >\n");
        for (int i = 1; i < columnList.size(); i++) {
            ColumnData data = columnList.get(i);
            String columnName = data.getColumnName();
            sb.append("\t<if test=\"").append(columnName).append(" != null ");
            //String 还要判断是否为空
            if ("String" == data.getDataType()) {
                sb.append(" and ").append(columnName).append(" != ''");
            }
            sb.append(" \">\n\t\t");
            sb.append(columnName + "=#{" + columnName + "},\n");
            sb.append("\t</if>\n");
        }
        sb.append("\t</trim>");
        String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{" + cd.getColumnName() + "}";
        return update;
    }


    /**
     * <br>
     * <b>功能：</b>获取所有列名字<br>
     * <b>作者：</b>zhoujf<br>
     * <b>日期：</b> 2013-12-21 <br>
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public String getColumnSplit(List<ColumnData> columnList) throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            commonColumns.append(data.getColumnName() + "|");
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }

    public Map<String, String> generateByUniqKeyForShardingDB(String tableName) throws SQLException {
        Map<String, String> resultMap = new HashMap<String, String>();
        String className = getTablesNameToClassName(tableName);
        String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1);
        List<IndexData> indexDataList = getIndexDatas(tableName);
        String mapper = "";
        String sql = "";
        for (IndexData indexData : indexDataList) {
            if (indexData.getIndexType() == 0 && !"PRIMARY".equals(indexData.keyName)) {
                String columnJoin = "";
                for (String column : indexData.getIndexFields()) {
                    columnJoin += String.format("@Param(\"%s\") String %s,", getcolumnNameToDomainPropertyName(column), getcolumnNameToDomainPropertyName(column));
                    sql += String.format(" %s =#{%s} and", column, getcolumnNameToDomainPropertyName(column));
                }
                sql = sql.substring(0, sql.length() - 3);//去掉最后的and
                String format = String.format("     void updateByUniqKey(%s @Param(\"data\") %s %s, @Param(\"suffix\") String suffix);\n", columnJoin, className, lowerName);
                String selectFormat = String.format("%s selectWithUniqKey(%s @Param(\"query\")%s %s, @Param(\"suffix\") String suffix);\n", className, columnJoin, className, lowerName);
                mapper = mapper + format + selectFormat;
                break;
            }
        }
        resultMap.put("generateByUniqKeyForShardingDB", mapper);
        resultMap.put("uniqCondition", sql);
        return resultMap;
    }

    public Map<String, String> generateByUniqKey(String tableName) throws SQLException {
        Map<String, String> resultMap = new HashMap<String, String>();
        String className = getTablesNameToClassName(tableName);
        String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1);
        List<IndexData> indexDataList = getIndexDatas(tableName);
        String mapper = "";
        String sql = "";
        for (IndexData indexData : indexDataList) {
            if (indexData.getIndexType() == 0 && !"PRIMARY".equals(indexData.keyName)) {
                String columnJoin = "";
                for (String column : indexData.getIndexFields()) {
                    columnJoin += String.format("@Param(\"%s\") String %s,", getcolumnNameToDomainPropertyName(column), getcolumnNameToDomainPropertyName(column));
                    sql += String.format(" %s =#{%s} and", column, getcolumnNameToDomainPropertyName(column));
                }
                sql = sql.substring(0, sql.length() - 3);//去掉最后的and
                String format = String.format("     void updateByUniqKey(%s @Param(\"data\") %s %s);\n", columnJoin, className, lowerName);
                String selectFormat = String.format("%s selectWithUniqKey(%s @Param(\"query\")%s %s);\n", className, columnJoin, className, lowerName);
                mapper = mapper + format + selectFormat;
                break;
            }
        }
        resultMap.put("generateByUniqKey", mapper);
        resultMap.put("uniqCondition", sql);
        return resultMap;
    }

    public String getParameterUniq(String tableName) throws SQLException {
        String result = "";
        List<IndexData> indexDataList = getIndexDatas(tableName);
        for (IndexData indexData : indexDataList) {
            if (indexData.getIndexType() == 0 && !"PRIMARY".equals(indexData.keyName)) {
                System.out.println(indexData);
                for (String column : indexData.getIndexFields()) {
                    String propertyName = getcolumnNameToDomainPropertyName(column);
                    result += String.format("item.get%s(), ", propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
                }
                System.out.println(tableName + "uniqKey:" + result);
                break;
            }
        }
        return result;
    }


}
