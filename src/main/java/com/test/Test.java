package com.test;

import com.alibaba.fastjson.JSONObject;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Test
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2018-10-25 16:49
 * @Version V1.0
 */
public class Test {
    
    public static void main(String [] args){

        /**
         * 通知第三方
         */
        Map<String, Object> notifyParams = new HashMap<String, Object>();

        notifyParams.put("outTradeNo", "outTradeNo");
        notifyParams.put("notifyUrl", "111");

        EventCreateRequest notifyEvent = new EventCreateRequest();
        notifyEvent.setCreateTime(new Date());
        notifyEvent.setDelayPublish(false);
        notifyEvent.setDetail(notifyParams);
        notifyEvent.setName("payment-notice");


        String notifyUrl = "222";
        notifyParams.put("notifyUrl", notifyUrl);


    }


}

class EventCreateRequest{
    /** 事件类型，必填项 */
    private String type;

    /** 业务名称，必填项 */
    private String name;

    /** 事件业务明细，必填项 */
    private Map<String, Object> detail;

    /** 创建时间 */
    private Date createTime;

    /** 是否延迟发布 */
    private boolean delayPublish;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDelayPublish() {
        return delayPublish;
    }

    public void setDelayPublish(boolean delayPublish) {
        this.delayPublish = delayPublish;
    }
}