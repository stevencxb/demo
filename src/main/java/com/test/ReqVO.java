package com.test;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo-wangqingsong on 2017/8/8.
 * 鹏华基金基本请求类
 */
public class ReqVO implements Serializable {
    //基本参数

    /**接口版本*/
    private String version = "1.0";
    /**签名类型*/
    private String signType = "RSA";
    /**字符编码*/
    private String charset = "UTF-8";
    /**电商编号:  必需*/
    private String ecno ="";
    /***电商渠道编号  非必需 **/
    private String channelCode = "";
    /**电商网点编号  非必需 **/
    private String branchCode = "";

    /**业务请求流水号*/
    private String serialNo = "ph"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    /**业务请求时间*/
    private String requestTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    /**签名 */
    private String sign;

    /**
     * 业务参数
     * 业务参数根据不同的接口参数不同
     * 具体参数key参考鹏华基金接口文档进行传参
     */
    private Map<String,Object> requestData;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getEcno() {
        return ecno;
    }

    public void setEcno(String ecno) {
        this.ecno = ecno;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Map<String, Object> getRequestData() {
        return requestData;
    }

    public void setRequestData(Map<String, Object> requestData) {
        this.requestData = requestData;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    @Override
    public String toString() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("version", this.getVersion());
        map.put("signType", this.getSignType());
        map.put("charset", this.getCharset());
        map.put("ecno", this.getEcno());
        map.put("serialNo", this.getSerialNo());
        map.put("requestTime", this.getRequestTime());
        map.put("sign", this.getSign());

        Map<String,Object> otherParmas = this.getRequestData();
        if (null == otherParmas){
            return JSONObject.toJSONString(map);
        }
        for (String key : otherParmas.keySet()) {
            if (null == otherParmas.get(key) ){

            }else{
                map.put(key, otherParmas.get(key));
            }

        }
        return  JSONObject.toJSONString(map);
    }
}
