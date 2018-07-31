package com.mapper.gh;

import com.mapper.Mapper114;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:19 2018/7/30
 */
@Mapper
public interface GhStoreMapper extends Mapper114 {
    List<String> findList();
}
