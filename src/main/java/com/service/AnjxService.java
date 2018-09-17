package com.service;

import com.mapper.anjx.AnjxMapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: HeQi
 * @Date:Create in 14:02 2018/9/17
 */
@Service
public class AnjxService {

    @Autowired
    private AnjxMapper anjxMapper;

    public SendingVo findOneHistory(SendingVo vo) {
        return anjxMapper.findOneHistory(vo);
    }

    public void updateHistory(SendingVo vo) {
        anjxMapper.updateHistory(vo);
    }

    public void insertSendHistoryAjx(SendingVo vo) {
        anjxMapper.insertSendHistoryAjx(vo);
    }
}
