package com;

import com.pojo.SendingVo;
import com.service.RptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:18 2018/7/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTo114 {

    @Autowired
    private RptService rptService;

    @Test
    public void ss(){
       List<SendingVo> list= rptService.findHistory();
        System.out.println(list.size());
    }
}
