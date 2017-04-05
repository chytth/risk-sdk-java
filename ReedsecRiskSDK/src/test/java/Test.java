import org.junit.runner.RunWith;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class Test {
    static Properties properties = new Properties();
    public static void main(String[] args) {
        writeProperties("a","b");

    }

    public static void writeProperties(String keyname,String keyvalue) {
        try {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            String url = Test.class.getResource("init.properties").getFile();
            OutputStream fos = new FileOutputStream(url,true);
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    @org.junit.Test
    public  void test(){
        System.out.println("adasda");
    }
}
