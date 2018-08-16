package com.service;

import com.mapper.mapper21.SignMapper21;
import com.pojo.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:25 2018/8/16
 */
@Service
public class SignService21 {

    @Autowired
    private SignMapper21 signMapper21;

    public List<UserSign> findUserSignByUidAndStore(Integer uid, String store, Integer type) {
       return signMapper21.findUserSignByUidAndStore(uid,store,type);
    }

    public String findMaxExpend2() {
        return signMapper21. findMaxExpend2();
    }
}
