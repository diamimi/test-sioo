package com;

import com.pojo.SmsUser;
import com.service.SmsUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        List<SmsUser> smsUsers = smsUserService.loadUser();
        for (SmsUser smsUser : smsUsers) {
           int count= smsUserService.findControl(smsUser.getId());
           if(count==0){
               System.out.println(smsUser.getId());
           }
        }
    }
}
