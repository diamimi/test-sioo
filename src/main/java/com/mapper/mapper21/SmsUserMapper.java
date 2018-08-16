package com.mapper.mapper21;

import com.pojo.SmsUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:06 2018/8/16
 */
@Mapper
public interface SmsUserMapper {
    List<SmsUser> loadUser();
}
