package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.UserDayCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:29 2018/7/24
 */
@Mapper
public interface UserDayCountMapper extends Mapper21 {
    List<UserDayCount> getUserDayCount();
}
