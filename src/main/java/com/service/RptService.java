package com.service;

import com.mapper.Mapper114;
import com.mapper.Mapper21;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */

@Service
public class RptService {


    @Autowired
    private Mapper21 mapper21;

    @Autowired
    private Mapper114 mapper114;

    public SendingVo findOne(String hisid) {
        return mapper21.findOne(hisid);
    }

    public List<SendingVo> findHistory() {
        return mapper21.findHistory();
    }

    public SendingVo findRptCode(long id) {
        return mapper21.findRptCode(id);
    }

    public int count() {
        return mapper114.count();
    }
}
