package com.util;

import com.pojo.SmsUser;
import com.service.SmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:07 2018/8/16
 */
@Component
public class SmsUserUtil {

    @Autowired
    private SmsUserService smsUserService;

    public void loadUser(){
       List<SmsUser> users= smsUserService.loadUser();
       users.forEach(u->SmsCache.USER.put(u.getId(), u));
    }
}
