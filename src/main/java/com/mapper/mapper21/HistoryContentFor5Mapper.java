package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.HistoryContentFor5;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:34 2018/7/23
 */
@Mapper
public interface HistoryContentFor5Mapper extends Mapper21 {
    List<HistoryContentFor5> findHistory(HistoryContentFor5 vo);

    void updateByCondition(HistoryContentFor5 vo);

    void add(SendingVo v);
}
