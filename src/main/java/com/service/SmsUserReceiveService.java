package com.service;

import com.mapper.mapper21.SmsUserReceiveMapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 16:51 2018/9/18
 */
@Service
public class SmsUserReceiveService {

    @Autowired
    private SmsUserReceiveMapper smsUserReceiveMapper;


    public List<SendingVo> findList(SendingVo vo) {
        return smsUserReceiveMapper.findList(vo);
    }
}
