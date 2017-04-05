package reedsec.application;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reedsec.tools.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static reedsec.services.RiskServiceImpl.SYS_CONFIG_MAP;
import static reedsec.tools.Utils.APPLICATION_JSON;

/**
 * Created by Administrator on 2017/3/25 0025.
 */

@Component("TaskJob")
public class TaskJob {

    private static Logger logger = Logger.getLogger(TaskJob.class);
    //获取token值，每隔一个小时重新获取一次
    @Scheduled()
    public static void one_hour_task(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.printf("定时任务执行:"+ df.format(new Date()));

        int is_token = Integer.valueOf(SYS_CONFIG_MAP.get("is_token"));
        if(is_token == 1){//开启token验证
            String token = getToken();
           try {
               JSONObject token_json = new JSONObject(token);
               if(token_json.has("jwt")){
                   SYS_CONFIG_MAP.put("token",token_json.getString("jwt"));
               }else{
                   System.out.println("没有获取到jwt");
               }

           }catch (Exception e){//出现异常解析出现错误，没有jwt
               System.out.println("cloud没有开启token验证");
           }

        }else{
            System.out.println("没有开启token验证，不执行定时查询");
        }
    }

    public static String getToken(){
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;

        try {
            String url=SYS_CONFIG_MAP.get("cloud_url")+"getAccessJwt";
            System.out.println("请求url：" + url);
            logger.debug("获取token: " + url);

            JSONObject token_json = new JSONObject();
            token_json.put("AppID", SYS_CONFIG_MAP.get("AppID"));
            token_json.put("AppSecret",SYS_CONFIG_MAP.get("AppSecret"));

            HttpPost httpPost = new HttpPost(url);
            StringEntity entityParams = new StringEntity(token_json.toString(), "UTF-8");
            System.out.println("发送请求：" + new String(EntityUtils.toByteArray(entityParams), "UTF-8"));

            entityParams.setContentType(Utils.CONTENT_TYPE_TEXT_JSON);
            entityParams.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

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
        return  res;
    }
}
