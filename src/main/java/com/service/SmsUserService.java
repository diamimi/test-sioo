package com.service;

import com.mapper.mapper21.SmsUserMapper;
import com.pojo.SmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:06 2018/8/16
 */
@Service
public class SmsUserService {

    @Autowired
    private SmsUserMapper smsUserMapper;

    public List<SmsUser> loadUser() {
        return smsUserMapper.loadUser();
    }
}
