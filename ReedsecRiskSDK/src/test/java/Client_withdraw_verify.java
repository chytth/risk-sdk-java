import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.json.JSONObject;
import reedsec.services.RiskService;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Client_withdraw_verify {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("account_id","1");
        json.put("account_name","abc");
        json.put("account_bankid","1");
        json.put("trans_type","abc");
        json.put("trans_id","1");
        json.put("device_type","1");
        json.put("device_id_type","1");
        json.put("device_id","1");
        json.put("client_ip","114.251.29.65");
        json.put("amount","1");
        RiskService riskVerifyService = getInterFace();
        String result = riskVerifyService.withdraw_verify(json.toString());
        System.out.println(result);
    }

    public static RiskService getInterFace(){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RiskService.class);
        factoryBean.setAddress("http://localhost:8080/server/riskService");
        return (RiskService) factoryBean.create();
    }
}
