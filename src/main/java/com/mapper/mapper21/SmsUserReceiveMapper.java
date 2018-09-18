package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.SendingVo;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 16:54 2018/9/18
 */
public interface SmsUserReceiveMapper extends Mapper21 {
    List<SendingVo> findList(SendingVo vo);
}
