package com.mapper.mapper114;

import com.mapper.Mapper114;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:46 2018/7/30
 */
@Mapper
public interface SendHistoryMapper114 extends Mapper114{
    List<SendingVo> findHistory(SendingVo vo);
}
