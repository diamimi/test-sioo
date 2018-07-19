package com.mapper;

import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */
@Mapper
public interface Mapper114 {
    SendingVo findOne(@Param("id") String hisid);

    List<SendingVo> findHistory();

    SendingVo findRptCode(long id);

    int count();

    List<SendingVo> getHistory(@Param("minHisid") int minHisid,@Param("maxHisid") int maxHisid);

    SendingVo getRptcodeByRpt(long id);

    SendingVo getRptcodeByHistory(long id);

    SendingVo getRptContent(long pid);

    List<SendingVo> findHistory114(Long i);

    void updateHistory114(SendingVo vo);

    List<SendingVo> findWz();

    void updateGhHistory(SendingVo vo);

    Integer findGhSucc(String uid);

    Integer findGhFail(String uid);

    Integer findGhTotal(String uid);

    Integer findGhSubFail(String uid);

    List<SendingVo> findFail();
}
