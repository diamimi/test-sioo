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

    List<SendingVo> findSingleHistory(SendingVo vo);

    List<String> getContentList(SendingVo vo);

    Integer getTotal(SendingVo vo);

    Integer getSucc(SendingVo vo);

    Integer getFail(SendingVo vo);

    Integer getWz(SendingVo vo);

    Integer getSingleTotal(SendingVo vo);

    List<SendingVo> findSingleHistoryGroupByContent(SendingVo vo);

    List<SendingVo> getFailReason(SendingVo vo);

    List<SendingVo> getPiCi(SendingVo vo);
}
