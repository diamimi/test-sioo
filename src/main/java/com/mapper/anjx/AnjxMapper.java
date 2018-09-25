package com.mapper.anjx;

import com.mapper.MapperAnjx;
import com.pojo.SendingVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:51 2018/7/19
 */
@Mapper
public interface AnjxMapper extends MapperAnjx {
    List<SendingVo> findOneHistory(SendingVo vo);

    void updateHistory(SendingVo vo);

    void insertSendHistoryAjx(SendingVo vo);
}
