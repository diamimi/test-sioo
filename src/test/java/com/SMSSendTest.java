package com;

import com.util.SmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
       String result= smsUtil.sendSms("【你我贷】尊敬的会员：凭身份证申请借款额度高至八千%,打开链接","18621367763");
        System.out.println(result);
    }
}
