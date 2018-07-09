package com.mapper;

import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */
@Mapper
public interface RptMapper {
    SendingVo findOne(@Param("id") String hisid);
}
