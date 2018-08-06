package com.service;

import com.mapper.mapper21.SendHistoryMapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:32 2018/7/23
 */
@Service
public class SendHistoryService {

    @Autowired
    private SendHistoryMapper sendHistoryMapper;

    public List<SendingVo> findByConditon(SendingVo vo){
        return sendHistoryMapper.findHistory(vo);
    }

    public void updateByCondition(SendingVo vo) {
        sendHistoryMapper.updateByCondition(vo);
    }

    public List<SendingVo> findHistoryAndRptcode(SendingVo vo) {
       return sendHistoryMapper.findHistoryAndRptcode(vo);
    }

    public int countHistoryAndRptcode(SendingVo vo) {
      return   sendHistoryMapper.countHistoryAndRptcode(vo);
    }

    public void batchInsert(List<SendingVo> subList) {
        sendHistoryMapper.batchInsert(subList);
    }

    public void insertHistory(SendingVo vo) {
        sendHistoryMapper.insertHistory(vo);
    }

    public List<SendingVo> export() {
      return   sendHistoryMapper.export();
    }
}
