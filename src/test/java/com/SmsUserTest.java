package com;

import com.pojo.SendingVo;
import com.service.SmsUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: HeQi
 * @Date:Create in 9:54 2018/8/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsUserTest {

    @Autowired
    private SmsUserService smsUserService;

    @Test
    public void noConsume() {
        SendingVo vo = new SendingVo();
        vo.setStarttime(20180601000000l);
        //List<SmsUser> users = smsUserService.findNoConsume(vo);
    }
}
