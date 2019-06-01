package com.hd.menstrualassistant.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 腾讯云短信推送接口
 *
 * @author zzl
 */
@Component
public class CloudMessagePushUtil {

    public static final Logger logger = LoggerFactory.getLogger(CloudMessagePushUtil.class);

    //POST https://yun.tim.qq.com/v5/tlssmssvr/sendsms?sdkappid=xxxxx&random=xxxx
    private static String sdkappid;
    private static String baseUrl;
    private static String sign;
    private static String tel_id;
    private static String nationcode;


    /**
     * 静态变量只能使用setter方式进行注入
     */
    @Value("${tencent.message.nationcode}")
    public void setNationcode(String nationcode) {
        CloudMessagePushUtil.nationcode = nationcode;
    }

    @Value("${tencent.message.sdkappid}")
    public void setSdkappid(String sdkappid) {
        CloudMessagePushUtil.sdkappid = sdkappid;
    }

    @Value("${tencent.message.baseurl}")
    public void setBaseUrl(String baseUrl) {
        CloudMessagePushUtil.baseUrl = baseUrl;
    }

    @Value("${tencent.message.sign}")
    public void setSign(String sign) {
        CloudMessagePushUtil.sign = sign;
    }

    @Value("${tencent.message.telid}")
    public void setTel_id(String tel_id) {
        CloudMessagePushUtil.tel_id = tel_id;
    }

    /*
   推送模版：

    {
        "ext": "",
        "extend": "",
        "params": [
            "验证码",
            "1234",
            "4"
        ],
        "sig": "ecab4881ee80ad3d76bb1da68387428ca752eb885e52621a3129dcf4d9bc4fd4",
        "sign": "腾讯云",
        "tel": {
            "mobile": "13788888888",
            "nationcode": "86"
        },
        "time": 1457336869,
        "tpl_id": 19
    }
     */

    /**
     * 推送演示
     *
     * @param phone
     * @param content
     * @throws IOException
     */
    public static void pushMessage(String phone, String content) throws IOException {
        long random = ThreadLocalRandom.current().nextLong(1000000000l, 9999999999l);
        String[] params = {};
        long unixTime = System.currentTimeMillis();
        String sig = SHA256Util.SHA256(String.format("appkey=%s&random=%s&time=%s&mobile=%s", sdkappid, random, unixTime, phone));//sha256
        JSONObject obj = new JSONObject();
        obj.put("params", params);
        obj.put("sig", sig);
        obj.put("sign", sign);
        obj.put("tel", new HashMap<String, String>() {{
            put("mobile", phone);
            put("nationcode", nationcode);
        }});
        logger.info(obj.toJSONString());
        String result = Request.Post(baseUrl + "?sdkappid=" + sdkappid + "&random=" + random)
                .bodyString(obj.toJSONString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        logger.info(result);
    }

    public static void main(String[] args) throws IOException {
        CloudMessagePushUtil.pushMessage("1", "1");
    }
}
