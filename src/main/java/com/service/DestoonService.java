package com.service;

import com.mapper.destoon.DestoonMapper;
import com.pojo.Area;
import com.pojo.IndustryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 9:23 2018/8/2
 */
@Service
public class DestoonService {

    @Autowired
    private DestoonMapper destoonMapper;

    public List<IndustryInfo> findIndustryInfoList() {
        return destoonMapper.findIndustryInfoList();
    }

    public IndustryInfo findIndustry(int id) {
        return destoonMapper.findIndustry(id);
    }

    public List<Area> findAreaList() {
        return destoonMapper.findAreaList();
    }

    public Area findArea(Integer areaid) {
        return destoonMapper.findArea(areaid);
    }
}
