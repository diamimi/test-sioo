package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:21 2018/7/30
 */
@Mapper
public interface Store21Mapper extends Mapper21{
    List<String> findList();

    List<SendingVo> singHistoryGroupByContent(SendingVo vo);
}
