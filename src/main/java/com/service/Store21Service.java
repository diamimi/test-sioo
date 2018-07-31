package com.service;

import com.mapper.mapper21.Store21Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:18 2018/7/30
 */
@Service
public class Store21Service {

    @Autowired
    private Store21Mapper store21Mapper;

    public List<String> findList() {
        return store21Mapper.findList();
    }
}
