package com;

import com.util.HttpClientUtil;
import com.util.SmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HeQi
 * @Date:Create in 15:12 2018/7/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSSendTest {

    @Autowired
    private SmsUtil smsUtil;

    @Test
    public void send(){
       String result= smsUtil.sendSms("【集借号】您的验证码为：123456，请妥善保管","18621367763");
        System.out.println(result);
    }

    @Test
    public void get(){
        Map<String, String> param = new HashMap<>();
        param.put("uid", "90006");
        param.put("auth", "92800d2fadc2c229a8d87a6cd4f2644c");
        String result = HttpClientUtil.doPost("http://47.96.75.8:9011/hy/rpt", param);
        System.out.println(result);
    }
}
