package codeGenerate.def;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author zhoujf
 * 
 */
public class CodeResourceUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("database");
	private static final ResourceBundle bundlePath = ResourceBundle.getBundle("config");
	
	//------------------------------------------------------------------------------------------------
	/**数据库连接：驱动*/
	public static  String DIVER_NAME = "";
	/**数据库连接：数据库URL*/
	public static  String URL = "";
	/**数据库连接：账号*/
	public static  String USERNAME = "";
	/**数据库连接：密码*/
	public static  String PASSWORD = "";
	/**数据库名*/
	public static  String DATABASE_NAME = "";

	/**
	 * 数据库类型
	 */
	public static String DATABASE_TYPE = "mysql";
	public static String DATABASE_TYPE_MYSQL = "mysql";
	public static String DATABASE_TYPE_ORACLE = "oracle";
	
	/**默认前4个字段必须输入*/
	public static String JEECG_UI_FIELD_REQUIRED_NUM = "4";
	/**默认页面生成4个查询字段*/
	public static String UI_FIELD_SEARCH_NUM = "3";
	
	
	//----------------------------------------------------------------------------------------------
	/**
	 * 各种包路径
	 */
	/**web根目录*/
	public static String web_root_package = "WebRoot";
	/**src根目录*/
	public static  String source_root_package = "src";
	/**业务包：不要随意改*/
	public static  String bussiPackage = "sun";
	public static  String bussiPackageUrl ="sun";
	/**业务包：不要随意改*/
	public static  String entity_package = "entity";
	/**业务包：不要随意改*/
	public static  String page_package = "page";
	
	//----------------------------------------------------------------------------------------------
	/**
	 * 代码生成路径
	 */
	/**entity 生成路径*/
	public static  String ENTITY_URL;
	/**Page 生成路径*/
	public static  String PAGE_URL;
	/**entity*/
	public static  String ENTITY_URL_INX;
	/**page*/
	public static  String PAGE_URL_INX;
	/**模板路径*/
	public static  String TEMPLATEPATH;
	/**Java文件生成路径*/
	public static  String CODEPATH;
	/**JSP文件生成路径*/
	public static  String JSPPATH;
	/**自定义主键*/
	public static  String GENERATE_TABLE_ID;
	/**自定义页面过滤字段*/
	public static  String JEECG_GENERATE_UI_FILTER_FIELDS;
	/**编码格式*/
	public static  String SYSTEM_ENCODING;
	//----------------------------------------------------------------------------------------------
	static{
		DIVER_NAME = getDIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();
		
		SYSTEM_ENCODING=getSYSTEM_ENCODING();
		TEMPLATEPATH =getTEMPLATEPATH();
		source_root_package=getSourceRootPackage();
		bussiPackage = getBussiPackage();
		bussiPackageUrl = bussiPackage.replace(".", "/");


		//自定义主键
		GENERATE_TABLE_ID =getGenerate_table_id();

		UI_FIELD_SEARCH_NUM =getUi_search_filed_num();
		
		
		
		//设置数据库类型
		if(URL.indexOf("mysql")>=0||URL.indexOf("MYSQL")>=0){
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		}else if(URL.indexOf("oracle")>=0||URL.indexOf("ORACLE")>=0){
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
		}
		
		
		source_root_package = source_root_package.replace(".", "/");
		web_root_package = web_root_package.replace(".", "/");
		
		//entity 生成路径
		ENTITY_URL = source_root_package+"/"+bussiPackageUrl+"/"+entity_package+"/";
		//Page 生成路径
		PAGE_URL = source_root_package+"/"+bussiPackageUrl+"/"+page_package+"/";
		//entity
		ENTITY_URL_INX = bussiPackage+"."+entity_package+".";
		//page
		PAGE_URL_INX = bussiPackage+"."+page_package+".";
		//Java文件生成路径
		CODEPATH = source_root_package+"/"+bussiPackageUrl+"/";
		//JSP文件生成路径
		JSPPATH = web_root_package+"/"+bussiPackageUrl+"/";
	}
	



	/**
	 *DIVER_NAME
	 * 
	 * @return
	 */
	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name");
	}

	/**
	 * URL
	 * 
	 * @return
	 */
	public static final String getURL() {
		return bundle.getString("url");
	}

	/**
	 * USERNAME
	 * 
	 * @return
	 */
	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	/**
	 * PASSWORD
	 * 
	 * @return
	 */
	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	/**
	 *DATABASE_NAME
	 * 
	 * @return
	 */
	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}
	
	private static String getBussiPackage() {
		return bundlePath.getString("bussi_package");
	}
	
	/**
	 *ENTITY_URL
	 * 
	 * @return
	 */
	public static final String getEntityPackage() {
		return bundlePath.getString("entity_package");
	}
	/**
	 *PAGE_URL
	 * 
	 * @return
	 */
	public static final String getPagePackage() {
		return bundlePath.getString("page_package");
	}
	
	/**
	 *TEMPLATEPATH
	 * 
	 * @return
	 */
	public static final String getTEMPLATEPATH() {

		return bundlePath.getString("templatepath_ftl");
	}

	
	/**
	 *CODEPATH
	 * 
	 * @return
	 */
	public static final String getSourceRootPackage() {
		return bundlePath.getString("source_root_package");
	}
	
	/**
	 *SYSTEM_ENCODING
	 * 
	 * @return
	 */
	public static final String getSYSTEM_ENCODING() {
		return bundlePath.getString("system_encoding");
	}
	/**
	 *jeecg_generate_table_id
	 * 
	 * @return
	 */
	public static final String getGenerate_table_id() {
		return bundlePath.getString("generate_table_id");
	}
	/**
	 *jeecg_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getGenerate_ui_filter_fields() {
		return bundlePath.getString("generate_ui_filter_fields");
	}
	/**
	 *jeecg_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getUi_search_filed_num() {
		return bundlePath.getString("ui_search_filed_num");
	}
	/**
	 *jeecg_generate_ui_filter_fields
	 * 
	 * @return
	 */
	public static final String getUi_field_required_num() {
		return bundlePath.getString("ui_field_required_num");
	}
	
	
	public static final String getConfigInfo(String key) {
		return bundlePath.getString(key);
	}
}
