package com.service;

import com.mapper.mapper21.SendHistoryMapper;
import com.pojo.SendingVo;
import com.sioo.client.cmpp.vo.DeliverVo;
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

    public Integer countTotal(SendingVo vo) {
        return sendHistoryMapper.countTotal(vo)==null?0:sendHistoryMapper.countTotal(vo);
    }

    public Integer countSucc(SendingVo vo) {
        return sendHistoryMapper.countSucc(vo)==null?0:sendHistoryMapper.countSucc(vo);
    }

    public Integer countFail(SendingVo vo) {
        return sendHistoryMapper.countFail(vo)==null?0:sendHistoryMapper.countFail(vo);

    }

    public void updateToSuccess(SendingVo sendingVo) {
        sendHistoryMapper.updateToSuccess(sendingVo);
    }

    public SendingVo findSucc21(SendingVo sendingVo) {
        return sendHistoryMapper.findSucc21(sendingVo);
    }

    public List<DeliverVo> findRptPush(SendingVo vo) {
        return sendHistoryMapper.findRptPush(vo);
    }

    public List<String> getIds() {
        return  sendHistoryMapper.getIds();
    }

    public SendingVo findById(SendingVo vo) {
        return sendHistoryMapper.findById(vo);
    }

    public SendingVo getRptBacks(SendingVo vo) {
        return sendHistoryMapper.getRptBacks(vo);
    }

    public void updateToFail(SendingVo vo) {
        sendHistoryMapper.updateToFail(vo);
    }

    public void inserBlackMobile(SendingVo vo) {
        sendHistoryMapper.inserBlackMobile(vo);
    }

    public int checkBlackMobileExsit(String mobile) {
        return sendHistoryMapper.checkBlackMobileExsit(mobile);
    }
}
