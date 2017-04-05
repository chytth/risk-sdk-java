import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.json.JSONObject;
import reedsec.services.RiskService;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Client_register_notify {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("account_id","13521452365");
        json.put("register_channel","abc");
        json.put("device_type","abc");
        json.put("device_id_type","abc");
        json.put("device_id","b000001");
        json.put("client_ip","114.251.29.65");
        RiskService riskVerifyService = getInterFace();
        String result = riskVerifyService.register_notify(json.toString());
        System.out.println(result);
    }

    public static RiskService getInterFace(){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RiskService.class);
        factoryBean.setAddress("http://localhost:8080/server/riskService");
        return (RiskService) factoryBean.create();
    }
}
