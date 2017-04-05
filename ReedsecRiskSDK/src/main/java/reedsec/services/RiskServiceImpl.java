package reedsec.services;

import reedsec.application.TaskJob;
import reedsec.tools.Utils;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@WebService(endpointInterface = "reedsec.services.RiskService")
public class RiskServiceImpl implements RiskService {

    //用来存放全局的系统参数
    public static Map<String,String> SYS_CONFIG_MAP = new HashMap<String,String>();
    //初始化
    public void init_properties(String partner_id, String partner_key, String app, String is_token, String AppID, String AppSecret) {
        SYS_CONFIG_MAP.put("partner_id",partner_id);
        SYS_CONFIG_MAP.put("partner_key",partner_key);
        SYS_CONFIG_MAP.put("app",app);
        SYS_CONFIG_MAP.put("is_token",is_token);
        SYS_CONFIG_MAP.put("AppID",AppID);
        SYS_CONFIG_MAP.put("AppSecret",AppSecret);

        //读取配置文件的cloud_url
        Properties properties = Utils.loadConfig();
        SYS_CONFIG_MAP.put("cloud_url",properties.getProperty("cloud_url"));
        TaskJob.one_hour_task();
    }


    /**--------    验证类    --------**/
    //贷前验证
    public String loan_verify(String json) {
        String result = Utils.postApi("loan_verify","riskVerify",json);
        return result;
    }

    //发券（红包）前验证
    public String coupon_verify(String json) {
        String result = Utils.postApi("coupon_verify","riskVerify",json);
        return result;
    }

    //发送短信验证码判断
    public String sms_verify(String json) {
        String result = Utils.postApi("sms_verify","riskVerify",json);
        return result;
    }

    //提现前验证
    public String withdraw_verify(String json) {
        String result = Utils.postApi("withdraw_verify","riskVerify",json);
        return result;
    }

    //**--------    通知类    --------**//*
    //注册后通知
    public String register_notify(String json) {
        String result = Utils.postApi("register_notify","riskNotify",json);
        return result;
    }

    //登陆后通知
    public String login_notify(String json) {
        String result = Utils.postApi("login_notify","riskNotify",json);
        return result;
    }

    //充值后通知
    public String store_notify(String json) {
        String result = Utils.postApi("store_notify","riskNotify",json);
        return result;
    }
}
