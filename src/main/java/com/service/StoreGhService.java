package com.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.mapper.gh.GhStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:17 2018/7/30
 */
@Service
public class StoreGhService {


    @Autowired
    private GhStoreMapper ghStoreMapper;

    public List<String> findList() {
        return ghStoreMapper.findList();
    }
}
