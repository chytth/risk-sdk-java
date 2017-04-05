import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import reedsec.services.RiskService;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Client_coupon_verify {
    private static Logger logger = Logger.getLogger(Client_coupon_verify.class);
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("account_id","a000001");
        json.put("trans_type","abc");
        json.put("device_type","abc");
        json.put("device_id","abc");
        json.put("client_ip","b000001");
        json.put("coupon_type","c000001");
        json.put("coupon_amount","abc");
        json.put("coupon_desc","abc");
        json.put("coupon_item","114.251.29.65");
        json.put("device_id_type","1");
        RiskService riskVerifyService = getInterFace();
//        riskVerifyService.init_properties("100104","AXCOBpnHGhY9","web","1","ssj0987","ssjqwerty");
        String result = riskVerifyService.coupon_verify(json.toString());
        System.out.println(result);
    }

    public static RiskService getInterFace(){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RiskService.class);
        factoryBean.setAddress("http://localhost:8080/server/riskService");
        return (RiskService) factoryBean.create();
    }
}
