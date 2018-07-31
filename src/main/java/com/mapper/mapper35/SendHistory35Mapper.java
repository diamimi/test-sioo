package com.mapper.mapper35;

import com.mapper.Mapper114;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:34 2018/7/23
 */
@Mapper
public interface SendHistory35Mapper extends Mapper114 {
    List<SendingVo> findHistory(SendingVo vo);

    void updateByCondition(SendingVo vo);

    List<SendingVo> findHistoryAndRptcode(SendingVo vo);

    int countHistoryAndRptcode(SendingVo vo);
}
