package com.mapper.gh;

import com.mapper.MapperGh;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:19 2018/7/30
 */
@Mapper
public interface GhStoreMapper extends MapperGh {
    List<String> findList();
}
