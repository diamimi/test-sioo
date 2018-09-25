package com.service;

import com.mapper.mapper114.SendHistoryMapper114;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:46 2018/7/30
 */
@Service
public class SendHistoryService114 {
    @Autowired
    private SendHistoryMapper114 sendHistoryMapper114;

    public List<SendingVo> findHistory( SendingVo vo) {
        return sendHistoryMapper114.findHistory(vo);
    }

    public List<SendingVo> findSingleHistory(SendingVo vo) {
        return sendHistoryMapper114.findSingleHistory(vo);
    }

    public List<String> getContentList(SendingVo vo) {
       return sendHistoryMapper114.getContentList(vo);
    }

    public Integer getTotal(SendingVo vo) {
        return sendHistoryMapper114.getTotal(vo);
    }

    public Integer getSucc(SendingVo vo) {
        return sendHistoryMapper114.getSucc(vo);
    }

    public Integer getFail(SendingVo vo) {
        return sendHistoryMapper114.getFail(vo);
    }

    public Integer getWz(SendingVo vo) {
        return sendHistoryMapper114.getWz(vo);
    }

    public Integer getSingleTotal(SendingVo vo) {
        return sendHistoryMapper114.getSingleTotal(vo);
    }

    public List<SendingVo> findSingleHistoryGroupByContent(SendingVo vo) {
        return sendHistoryMapper114.findSingleHistoryGroupByContent(vo);
    }

    public List<SendingVo> getFailReason(SendingVo vo) {
        return sendHistoryMapper114.getFailReason(vo);
    }

    public List<SendingVo> getPiCi(SendingVo vo) {
       return sendHistoryMapper114.getPiCi(vo);
    }

    public void insertSendHistoryAjx(SendingVo vo) {
        sendHistoryMapper114.insertSendHistoryAjx(vo);
    }

    public List<SendingVo> findHistorySucc(SendingVo vo) {
        return sendHistoryMapper114.findHistorySucc(vo);
    }

    public List<SendingVo> getSuccList(SendingVo vo) {
        return sendHistoryMapper114.getSuccList(vo);
    }

    public List<SendingVo> getSuccBatchList(SendingVo vo) {
        return sendHistoryMapper114.getSuccBatchList(vo);
    }

    public void updateRpt(SendingVo vo) {
        sendHistoryMapper114.updateRpt(vo);
    }
}
