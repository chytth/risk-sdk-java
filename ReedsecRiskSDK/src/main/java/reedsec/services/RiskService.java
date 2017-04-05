package reedsec.services;

import javax.jws.WebService;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@WebService
public interface RiskService {

    void init_properties(String partner_id,String partner_key,String app,String is_token,String AppID,String AppSecret);
    String loan_verify(String json);
    String coupon_verify(String json);
    String sms_verify(String json);
    String withdraw_verify(String json);
    String register_notify(String json);
    String login_notify(String json);
    String store_notify(String json);

}

