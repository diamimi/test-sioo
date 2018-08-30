package com.mapper.mapper21;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:52 2018/8/27
 */
@Mapper
public interface BlackMobileMapper {
    List<String> getBlackMobile(@Param("minid") int minid, @Param("maxid") int maxid);
}
