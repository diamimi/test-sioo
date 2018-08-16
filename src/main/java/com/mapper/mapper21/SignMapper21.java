package com.mapper.mapper21;

import com.pojo.UserSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:26 2018/8/16
 */
@Mapper
public interface SignMapper21 {
    List<UserSign> findUserSignByUidAndStore(@Param("uid") Integer uid, @Param("store")String store, @Param("type")Integer type);

    String findMaxExpend2();
}
