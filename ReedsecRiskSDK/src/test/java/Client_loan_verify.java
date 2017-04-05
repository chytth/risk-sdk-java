import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.json.JSONObject;
import reedsec.services.RiskService;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class Client_loan_verify {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
//        json.put("userId","a000001");
//        json.put("name","abc");
//        json.put("idNum","abc");
//        json.put("phoneNum","abc");
//        json.put("bankAccount","b000001");
//        json.put("udid","c000001");
//        json.put("qqNum","abc");
//        json.put("email","abc");
//        json.put("ip","114.251.29.65");
//        json.put("trans_id","abc");

        json.put("account_id","12824324171");
        json.put("account_idcard","421083199014212541");
        json.put("account_name","测试");
        json.put("trans_type","loan");
        json.put("device_type","app");
        json.put("device_id","123");
        json.put("device_id_type","loan");
        json.put("client_ip","114.251.29.65");
        json.put("amount","1");
        RiskService riskVerifyService = getInterFace();
        riskVerifyService.init_properties("100107","Aa9pA3GsBc7a","web","1","admin","admin6789");
//        riskVerifyService.init_properties("100104","AXCOBpnHGhY9","web","1","ssj0987","ssjqwerty");
        String result = riskVerifyService.loan_verify(json.toString());
        System.out.println(result);
    }

    public static RiskService getInterFace(){
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RiskService.class);
        factoryBean.setAddress("http://localhost:8080/server/riskService");
        return (RiskService) factoryBean.create();
    }
}
