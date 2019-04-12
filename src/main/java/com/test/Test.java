package com.test;

import com.alibaba.fastjson.JSONObject;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @Title: Test
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2018-10-25 16:49
 * @Version V1.0
 */
public class Test {
    
    public static void main(String [] args){

        ReqVO reqVO = new ReqVO();
        reqVO.setEcno("MJ_102");
        JSONObject data =  new JSONObject();
        data.put("orderNo",1111111);
        reqVO.setRequestData(data);
        JSONObject result = null;
        try {
            result = PhFundUtil.execute("https://m.test.phfund.com.cn/openapi/addBankCardQuery", reqVO);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(result);

//        System.out.println(64 << 1);

//        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
//        for (GarbageCollectorMXBean bean:list) {
//            System.out.println(bean.getName());
//        }
//
//
//        MyThread myThread = new MyThread();



    }


}
