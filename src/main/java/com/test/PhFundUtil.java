package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.http.HttpHandler;
import com.test.http.HttpRequest;
import com.test.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class PhFundUtil {


	/**
	 *
	 * @param postUrl
	 * @param authReq
	 * @return
	 * @throws Exception
	 */
	public static JSONObject execute(String postUrl, ReqVO authReq) throws Exception {
		String authReqJson = authReq.toString();

		ThirdSecurity thirdChannelSecurity = ThirdSecurity.getInstance();
		String reqData = SecurityUtil.signStrReplace(SecurityUtil
				.encryptByPublicKey(thirdChannelSecurity.getPhfundPublicKey(),
						authReqJson));
		String sign = SecurityUtil.signStrReplace(SecurityUtil
				.signByPrivateKey(thirdChannelSecurity.getChannelPrivateKey(),
						authReqJson));

		Map<String, Object> postParams = new HashMap<String, Object>();
		postParams.put("reqdata", SecurityUtil.signStrReplace(reqData));
		postParams.put("sign", SecurityUtil.signStrReplace(sign));
		postParams.put("partnerCode", JSON.parseObject(authReqJson).getString("ecno"));

		// 响应数据
		HttpResponse<String> httpResponse = HttpHandler.getInstance().execute(
				new HttpRequest().setUrl(postUrl).setParams(postParams)
						.setMethod(HttpRequest.METHOD_POST),
				HttpResponse.STRING);

		if (null == httpResponse.getResult()){
			System.out.println("调用鹏华服务失败:"+postUrl+"[参数：]"+authReqJson);
		}

		JSONObject result = JSON.parseObject(httpResponse.getResult());
		// 成功
		if (result.getString("status").equals("0000")) {
			String respSign = result.getString("sign");

			// 解密：用鹏华的私钥证书，对加密数据进行解密，获得原始数据
			String respData = SecurityUtil.decryptByPrivateKey(
					//thirdChannelSecurity.getPhfundPrivateKey(),
					thirdChannelSecurity.getChannelPrivateKey(),
					SecurityUtil.verifyStrReplace(result.getString("respdata")));

			// 验签：用渠道的公钥证书，进行验签
			boolean verifyFlag = SecurityUtil.verifyByPublicKey(
					thirdChannelSecurity.getPhfundPublicKey(),
					//thirdChannelSecurity.getChannelPublicKey(),
					respData,SecurityUtil.verifyStrReplace(respSign));

			if(verifyFlag) {
				JSONObject respDataObject = JSON.parseObject(respData);
				result.put("respdata", respDataObject);
			} else {
				result.put("respdata", null);
				result.put("retMsg", "响应数据验签失败");
				result.put("status", "1000");
			}
		} else {
			String respSign = result.getString("sign");

			// 解密：用鹏华的私钥证书，对加密数据进行解密，获得原始数据
			String respData = SecurityUtil.decryptByPrivateKey(
					thirdChannelSecurity.getChannelPrivateKey(),
					SecurityUtil.verifyStrReplace(result.getString("respdata")));
			System.out.println("鹏华出现错误返回的数据："+respData);
			result.put("respdata", null);
			result.put("status", "1000");
			result.put("code",result.getString("status"));
		}
		return result;
	}

}
