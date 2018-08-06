package com.mapper.mapper21;

import com.mapper.Mapper21;
import com.pojo.MobileArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:06 2018/8/3
 */
@Mapper
public interface MobileAreaMapper21 extends Mapper21{
    List<MobileArea> findList();
}
