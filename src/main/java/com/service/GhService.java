package com.service;

import com.mapper.gh.GhMapper;
import com.pojo.SendingVo;
import com.pojo.SmsUser;
import com.pojo.UserDayCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:53 2018/7/19
 */
@Service
public class GhService {

    @Autowired
    private GhMapper ghMapper;

    public List<SendingVo> findWz() {
        return ghMapper.findWz();
    }

    public SmsUser findUser(SmsUser smsUser) {
        return  ghMapper.findUser(smsUser);
    }

    public void updateByCondition(SmsUser u) {
          ghMapper.updateByCondition(u);
    }

    public List<UserDayCount> getGhUserDayCount() {
        return ghMapper.getGhUserDayCount();
    }
}
