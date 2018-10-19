package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.SendingVo;
import com.sioo.client.cmpp.vo.DeliverVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:34 2018/7/23
 */
@Mapper
public interface SendHistoryMapper extends Mapper21 {
    List<SendingVo> findHistory(SendingVo vo);

    void updateByCondition(SendingVo vo);

    List<SendingVo> findHistoryAndRptcode(SendingVo vo);

    int countHistoryAndRptcode(SendingVo vo);

    void batchInsert(@Param("list") List<SendingVo> subList);

    void insertHistory(SendingVo vo);

    List<SendingVo> export();

    Integer countTotal(SendingVo vo);

    Integer countSucc(SendingVo vo);

    Integer countFail(SendingVo vo);

    void updateToSuccess(SendingVo sendingVo);

    SendingVo findSucc21(SendingVo sendingVo);

    List<DeliverVo> findRptPush(SendingVo vo);

    List<String> getIds();

    SendingVo findById(SendingVo vo);

    SendingVo getRptBacks(SendingVo vo);

    void updateToFail(SendingVo vo);
}
