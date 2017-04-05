import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.json.JSONObject;
import reedsec.services.RiskService;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Client_login_notify {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("account_id","1");
        json.put("device_type","1");
        json.put("device_id_type","abc");
        json.put("device_id","1");
        json.put("client_ip","24.21.21.14");
        json.put("login_result","0");

        RiskService riskVerifyService = getInterFace();
        String result = riskVerifyService.login_notify(json.toString());
        System.out.println(result);
    }

    public static RiskService getInterFace(){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RiskService.class);
        factoryBean.setAddress("http://localhost:8080/server/riskService");
        return (RiskService) factoryBean.create();
    }
}
