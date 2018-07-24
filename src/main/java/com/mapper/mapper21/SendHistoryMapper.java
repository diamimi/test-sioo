package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:34 2018/7/23
 */
@Mapper
public interface SendHistoryMapper extends Mapper21 {
    List<SendingVo> findHistory(SendingVo vo);

    void updateByCondition(SendingVo vo);
}
