package reedsec.tools;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoaderListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import static reedsec.services.RiskServiceImpl.SYS_CONFIG_MAP;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class Utils {
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static Logger logger = Logger.getLogger(Utils.class);
    private static String partner_id ;
    private static String partner_key;
    private static String app;


    public static String postApi(String event_type,String method_type,String json){

        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        try {
            partner_id = SYS_CONFIG_MAP.get("partner_id");
            partner_key = SYS_CONFIG_MAP.get("partner_key");
            app = SYS_CONFIG_MAP.get("app");

            JSONObject request_body = new JSONObject();
            request_body.put("event_type",event_type);//事件类型
            request_body.put("app",app);//事件来源
            request_body.put("form_string",new JSONObject(json));

            JSONObject request_json = new JSONObject();
            request_json.put("partner_id",Integer.valueOf(partner_id));
            request_json.put("partner_key",partner_key);
//            request_json.put("device_cid","");
            request_json.put("request_body",request_body);
//            System.out.println(request_json.toString());
            logger.debug("请求的json数据："+ request_json.toString());

            String url=SYS_CONFIG_MAP.get("cloud_url")+method_type;
            System.out.println("请求url：" + url);
            logger.debug("请求url：" + url);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            if(Integer.valueOf(SYS_CONFIG_MAP.get("is_token")) == 1){
                httpPost.setHeader("Authorization","JWT "+ SYS_CONFIG_MAP.get("token"));
            }

            StringEntity entityParams = new StringEntity(request_json.toString(), "UTF-8");
            System.out.println("发送请求：" + new String(EntityUtils.toByteArray(entityParams), "UTF-8"));

            entityParams.setContentType(CONTENT_TYPE_TEXT_JSON);
            entityParams.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(entityParams);

            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();

            if (response == null || httpEntity == null) {
                res = "操作失败";
            }
            res = new String(EntityUtils.toByteArray(httpEntity), "UTF-8");
            System.out.println("请求结果：" + res);
            logger.debug("请求结果：" + res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    //读取配置
    public static Properties loadConfig(){
        try{
            Properties properties = new Properties();
            // 读取SRC下配置文件 --- 属于读取内部文件
            properties.load(ContextLoaderListener.class.getResourceAsStream("/init.properties"));
            //读取外部的配置文件，配置文件和执行jar在同一目录
//            String filePath = System.getProperty("user.dir")
//                    + "/web/WEB-INF/init.properties";
//            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
//            properties.load(in);
            return properties;
        } catch (IOException e) {
            System.out.println("读取配置信息出错！"+ e);
            return null;
        }
    }

    /**
     * 更新（或插入）一对properties信息(主键及其键值)
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * @param keyname 键名
     * @param keyvalue 键值
     */

    public static void writeProperties(String keyname,String keyvalue) {
        try {
            Properties properties = new Properties();
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream("init.properties");
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }


    /**
     * 更新properties文件的键值对
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * @param keyname 键名
     * @param keyvalue 键值
     */

    public static void updateProperties(String keyname,String keyvalue) {
        try {
            Properties properties = new Properties();
            properties.load(reedsec.application.ContextLoaderListener.class.getResourceAsStream("/init.properties"));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            String url = reedsec.application.ContextLoaderListener.class.getResource("/init.properties").getFile();
            OutputStream fos = new FileOutputStream(url);
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
}
