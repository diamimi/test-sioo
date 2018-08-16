package com.service;

import com.mapper.mapper21.HistoryContentFor5Mapper;
import com.pojo.HistoryContentFor5;
import com.pojo.SendingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:32 2018/7/23
 */
@Service
public class HistoryContentFor5Service {

    @Autowired
    private HistoryContentFor5Mapper historyContentFor5Mapper;

    public List<HistoryContentFor5> findByConditon(HistoryContentFor5 vo){
        return historyContentFor5Mapper.findHistory(vo);
    }

    public void updateByCondition(HistoryContentFor5 vo) {
        historyContentFor5Mapper.updateByCondition(vo);
    }

    public void add(SendingVo v) {
        historyContentFor5Mapper.add(v);
    }
}
