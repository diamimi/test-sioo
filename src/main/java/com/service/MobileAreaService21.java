package com.service;

import com.mapper.mapper21.MobileAreaMapper21;
import com.pojo.MobileArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:05 2018/8/3
 */
@Service
public class MobileAreaService21 {

    @Autowired
    private MobileAreaMapper21 mobileAreaMapper21;

    public List<MobileArea> findList() {
        return mobileAreaMapper21.findList();
    }
}
