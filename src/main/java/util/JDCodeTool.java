package util;

import codeGenerate.Generator;
import codeGenerate.TableInfo;
import codeGenerate.def.CodeResourceUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：根据数据库所有的表的生成代码
 *
 * @author：zhoujf
 * @version:1.0
 * @since
 */
public class JDCodeTool {
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    public static void main(String[] args) throws SQLException {
//        CreateBean createBean = new CreateBean();
//        createBean.setMysqlInfo(url, username, passWord);
//        List<TableInfo> tables = createBean.getTablesInfo();
//        for (TableInfo info : tables) {
//            List<TableInfo> in = new ArrayList<TableInfo>();
//            if (info.getTableName().equals("ccs_acct")) {
//
//                System.out.println(info.getTableName() + ";" + info.getTableComment());
//
//                in.add(info);
//            }
//            Generator.batchGenerateCode(in);
//        }
////        batchGenerateCode(tables);
//    }
String authMeta = "ccs_acct_o\n" +
                "ccs_acct_scene_policy\n" +
                "ccs_acct_scene_stat\n" +
                "ccs_authmemo_hst\n" +
                "ccs_authmemo_inq_log\n" +
                "ccs_authmemo_o\n" +
                "ccs_card_acq_stat\n" +
                "ccs_card_o\n" +
                "ccs_card_scene_policy\n" +
                "ccs_card_scene_stat\n" +
                "ccs_card_specbiz_ctrl\n" +
                "ccs_country_group_stat\n" +
                "ccs_cust_auth_ctrl\n" +
                "ccs_cust_o\n" +
                "ccs_cust_scene_policy\n" +
                "ccs_cust_scene_stat\n" +
                "ccs_lmt\n" +
                "ccs_lmt_adj\n" +
                "ccs_lmt_scene\n" +
                "ccs_manual_release_stat\n" +
                "ccs_mcc_merchant_parameter\n" +
                "ccs_mcc_merchant_stat\n" +
                "ccs_mcc_scene_stat\n" +
                "ccs_mcc_stat\n" +
                "ccs_mcg_group_stat\n" +
                "ccs_merchant_authorization_stat\n" +
                "ccs_merchant_group_stat\n" +
                "ccs_merchant_key_group_stat\n" +
                "ccs_pin_temp";
        String coreMeta = "ccs_accounting_mq_msg\n" +
                "ccs_acct\n" +
                "ccs_acct_bak\n" +
                "ccs_acct_info\n" +
                "ccs_acct_limit_log\n" +
                "ccs_acct_payment_init_hst_info\n" +
                "ccs_accting_flow\n" +
                "ccs_acru_int_bak\n" +
                "ccs_acru_int_bak_detail\n" +
                "ccs_auth_stat_scene\n" +
                "ccs_authmemo_hst\n" +
                "ccs_authmemo_match\n" +
                "ccs_authmemo_o\n" +
                "ccs_batch_status\n" +
                "ccs_card\n" +
                "ccs_card_close_reg\n" +
                "ccs_card_mapping\n" +
                "ccs_card_mapping_o\n" +
                "ccs_card_opt_didi_log\n" +
                "ccs_card_opt_log\n" +
                "ccs_card_renew_reg\n" +
                "ccs_card_secure_lock\n" +
                "ccs_cashloan_depo_log\n" +
                "ccs_cashloan_depo_log_hst\n" +
                "ccs_check_param_ctrl\n" +
                "ccs_china_division\n" +
                "ccs_commit_run_ctl\n" +
                "ccs_contribution_hst_info\n" +
                "ccs_corp_info\n" +
                "ccs_corp_reg\n" +
                "ccs_corp_stmt\n" +
                "ccs_corp_structure\n" +
                "ccs_cssfee_reg\n" +
                "ccs_cust\n" +
                "ccs_cust_address\n" +
                "ccs_cust_auth_policy\n" +
                "ccs_cust_auth_stat\n" +
                "ccs_cust_card\n" +
                "ccs_cust_employee\n" +
                "ccs_cust_linkman\n" +
                "ccs_cust_mcc_policy\n" +
                "ccs_cust_mcc_stat\n" +
                "ccs_cust_re_stat\n" +
                "ccs_delete_card_opt\n" +
                "ccs_host_posloan\n" +
                "ccs_lcode_blk_hst\n" +
                "ccs_loan\n" +
                "ccs_loan_reg\n" +
                "ccs_loan_reg_hst\n" +
                "ccs_loan_settle_record\n" +
                "ccs_lock_hst\n" +
                "ccs_mail_history\n" +
                "ccs_mcc_scene_policy\n" +
                "ccs_op_audit_log\n" +
                "ccs_op_operate_log\n" +
                "ccs_op_privilege\n" +
                "ccs_oper_hst\n" +
                "ccs_order\n" +
                "ccs_order_market_record\n" +
                "ccs_order_payment_init_hst_info\n" +
                "ccs_order_repay_record\n" +
                "ccs_plan\n" +
                "ccs_plan_sync\n" +
                "ccs_plan_sync_hst\n" +
                "ccs_posting_tmp\n" +
                "ccs_repay_hst\n" +
                "ccs_schedule\n" +
                "ccs_schedule_hst\n" +
                "ccs_sms\n" +
                "ccs_stmt\n" +
                "ccs_stmt_day_change_log\n" +
                "ccs_stmt_reprint\n" +
                "ccs_txn_adj\n" +
                "ccs_txn_gl\n" +
                "ccs_txn_hst\n" +
                "ccs_txn_manual\n" +
                "ccs_txn_manual_hst\n" +
                "ccs_txn_reject\n" +
                "ccs_txn_reverse_relation\n" +
                "ccs_warning\n" +
                "ccs_xfr_record\n" +
                "dispute_limit_use_record\n" +
                "gl_txn_adj\n" +
                "online_opt_log\n" +
                "pricing_acct_relations\n" +
                "pricing_basic_info";
        String[] tableNames = authMeta.split("\n");


        List<TableInfo> tables = new ArrayList<TableInfo>();
        for (int i = 0; i < tableNames.length; i++) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableNames[i]);
            tables.add(tableInfo);
        }
        Generator.batchGenerateCode(tables);
    }


}
