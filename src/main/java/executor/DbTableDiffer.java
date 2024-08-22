package executor;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.service.CreateBean;

public class DbTableDiffer {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) {
        CreateBean createBean = new CreateBean();
        createBean.setMysqlInfo(url, username, passWord);



    }
}

