package com.service;

import com.mapper.mapper21.UserDayCountMapper;
import com.pojo.UserDayCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:28 2018/7/24
 */
@Service
public class UserDayCountService {

    @Autowired
   private UserDayCountMapper userDayCountMapper;

    public List<UserDayCount> getUserDayCount(){
        return userDayCountMapper.getUserDayCount();
    }
}
