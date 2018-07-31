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
}
