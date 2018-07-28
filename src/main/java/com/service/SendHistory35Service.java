package com.service;

import com.mapper.mapper35.SendHistory35Mapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:32 2018/7/23
 */
@Service
public class SendHistory35Service {

    @Autowired
    private SendHistory35Mapper sendHistory35Mapper;

    public List<SendingVo> findByConditon(SendingVo vo){
        return sendHistory35Mapper.findHistory(vo);
    }

    public void updateByCondition(SendingVo vo) {
        sendHistory35Mapper.updateByCondition(vo);
    }

    public List<SendingVo> findHistoryAndRptcode(SendingVo vo) {
        return sendHistory35Mapper.findHistoryAndRptcode(vo);
    }
}
