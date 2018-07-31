package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HeQi
 * @Date:Create in 14:07 2017/12/27
 * 短信发送
 */
@Component
public class SmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    @Value("${sms.uid}")
    private String uid;

    @Value("${sms.expid}")
    private String expid;

    @Value("${sms.auth}")
    private String auth;

    @Value("${sms.url}")
    private String url;


    /**
     * @param content 短信内容
     * @param mobile  手机号码
     * @throws Exception
     */
    public String sendSms(String content, String mobile) {
        Map<String, String> param = new HashMap<>();
        param.put("uid", uid);
        param.put("auth", auth);
        param.put("expid", expid);
        param.put("mobile", mobile);
        try {
            param.put("msg", java.net.URLEncoder.encode(content, "gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HttpClientUtil.doPost(url, param);
        return result;

    }

}
