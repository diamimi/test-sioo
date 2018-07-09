package com.service;

import com.mapper.RptMapper;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */

@Service
public class RptService {



    @Autowired
    private RptMapper rptMapper;

    public SendingVo findOne(String hisid) {
       return rptMapper.findOne(hisid);
    }
}
