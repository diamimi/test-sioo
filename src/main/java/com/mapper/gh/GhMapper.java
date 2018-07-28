package com.mapper.gh;

import com.mapper.Mapper114;
import com.pojo.SendingVo;
import com.pojo.SmsUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:51 2018/7/19
 */
@Mapper
public interface GhMapper extends Mapper114 {
    List<SendingVo> findWz();

    SmsUser findUser(SmsUser smsUser);

    void updateByCondition(SmsUser u);
}
