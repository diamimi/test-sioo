package com.mapper;

import com.pojo.SendingVo;
import com.pojo.UserDayCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */
@Mapper
public interface Mapper21 {
    SendingVo findOne(@Param("id") String hisid);

    int findHistory(long id);



    SendingVo findRptCode(long id);

    void saveHistory(SendingVo vo);

    void saveRpt(SendingVo vo);

    void batchInsertHistory(@Param("list") List<SendingVo> list);

    void batchInsertRpt(List<SendingVo> list);

    List<UserDayCount> getGhUserDayCount();

    SendingVo findSendHistory21(long hisid);

    SendingVo findWz21(SendingVo vo);
}
