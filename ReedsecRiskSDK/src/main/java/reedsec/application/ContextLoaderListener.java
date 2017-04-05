
package reedsec.application;


import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/3/24 0023.
 */

public class ContextLoaderListener {


/**
     * 系统配置静态缓存map
     */

    //初始化方法
    @PostConstruct
    public static void init(){
        System.out.println("I'm  init  method  using  @PostConstrut....");
        //初始化启动执行定时任务
        TaskJob.one_hour_task();
    }


    //打jar包程序入口
    public static void main(String[] args) {
        init();
    }



}

