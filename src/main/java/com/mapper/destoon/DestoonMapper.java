package com.mapper.destoon;

import com.mapper.Mapper114;
import com.pojo.Area;
import com.pojo.IndustryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 9:23 2018/8/2
 */
@Mapper
public interface DestoonMapper extends Mapper114{
    List<IndustryInfo> findIndustryInfoList();

    IndustryInfo findIndustry(int id);

    List<Area> findAreaList();

    Area findArea(Integer areaid);
}
