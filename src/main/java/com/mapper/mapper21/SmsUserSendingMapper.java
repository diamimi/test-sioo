package com.mapper.mapper21;

import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:00 2018/8/4
 */
@Mapper
public interface SmsUserSendingMapper {
    List<SendingVo> findSmsUserSending(SendingVo vo);

    void updateSmsUserSending(SendingVo sendingVo);

    void insertSmsUserSending(SendingVo sendingVo);

    List<SendingVo> findList(SendingVo vo);
}
