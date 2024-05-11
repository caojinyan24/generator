package codeGenerate.def;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 *
 * @author zhoujf
 */
public class CodeResourceUtil {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("database");
    private static final ResourceBundle bundlePath = ResourceBundle.getBundle("config");

    //------------------------------------------------------------------------------------------------

    /**
     * 数据库连接：数据库URL
     */
    public static String URL = bundle.getString("url");
    /**
     * 数据库连接：账号
     */
    public static String USERNAME =bundle.getString("username");
    /**
     * 数据库连接：密码
     */
    public static String PASSWORD = bundle.getString("password");
    /**
     * 数据库名
     */
    public static String DATABASE_NAME =bundle.getString("database_name");


    /**
     * DATABASE_NAME
     *
     * @return
     */
    public static final String getDATABASE_NAME() {
        return bundle.getString("database_name");
    }


    /**
     * SYSTEM_ENCODING
     *
     * @return
     */
    public static final String getSYSTEM_ENCODING() {
        return bundlePath.getString("system_encoding");
    }

    public static final String getConfigInfo(String key) {
        return bundlePath.getString(key);
    }
}
