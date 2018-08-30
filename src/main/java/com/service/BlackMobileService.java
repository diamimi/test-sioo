package com.service;

import com.mapper.mapper21.BlackMobileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:51 2018/8/27
 */
@Service
public class BlackMobileService {

    @Autowired
    private BlackMobileMapper blackMobileMapper;

    public List<String> getBlackMobile(int minid, int maxid) {
        return blackMobileMapper.getBlackMobile(minid, maxid);
    }
}
