package com;

import com.service.SendHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:02 2018/7/25
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Anjxing {
    @Autowired
    private SendHistoryService sendHistoryService;

    @Test
    public void sss(){
       List<String> list=new ArrayList<>();
       list.stream().parallel().forEach(i->{
           if(i.equals("aaa")){
               return;
           }
       });
    }
}
