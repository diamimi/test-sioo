package com.service;

import com.mapper.mapper21.SmsUserSendingMapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:59 2018/8/4
 */
@Service
public class SmsUserSendingService {

    @Autowired
    private SmsUserSendingMapper smsUerSendingMapper;

    public List<SendingVo> findSmsUserSending(SendingVo vo) {
        return smsUerSendingMapper.findSmsUserSending(vo);
    }

    public void updateSmsUserSending(SendingVo sendingVo) {
        smsUerSendingMapper.updateSmsUserSending(sendingVo);
    }

    public void insertSmsUserSending(SendingVo sendingVo){
        smsUerSendingMapper.insertSmsUserSending(sendingVo);
    }

    public List<SendingVo> findList(SendingVo vo) {
       return smsUerSendingMapper.findList(vo);
    }
}
