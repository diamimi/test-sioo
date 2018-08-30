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

    public Integer getTotal(SendingVo vo) {
        return ghMapper.getTotal(vo);
    }

    public Integer getSucc(SendingVo vo) {
        return ghMapper.getSucc(vo)==null?0:ghMapper.getSucc(vo);
    }

    public Integer getFail(SendingVo vo) {
        return ghMapper.getFail(vo);
    }

    public Integer getWz(SendingVo vo) {
        return ghMapper.getWz(vo);
    }

    public List<SendingVo> getHistorySucc(SendingVo vo) {
        return ghMapper.getHistorySucc(vo);
    }

    public List<SmsUser> findUserList(SmsUser smsUser) {
        return ghMapper.findUserList(smsUser);
    }

    public UserDayCount findUserDayCount(UserDayCount userDayCount) {
        return ghMapper.findUserDayCount(userDayCount);
    }
}
