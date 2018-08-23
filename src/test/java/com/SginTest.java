package com;

import com.util.FileRead;
import com.util.StoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: HeQi
 * @Date:Create in 9:02 2018/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SginTest {

     @Autowired
    private StoreUtil storeUtil;

     @Test
     public void ss(){
         List<String> list = FileRead.getInstance().read("D:\\hq\\files/11.txt", "GBK");
         Set<String> set=new HashSet<>();
         list.stream().forEach(s->set.add(storeUtil.getSign(s)));
         set.stream().forEach(e-> System.out.println(e));

     }

     @Test
    public void sss(){
         String s="【万家乐】天天追延禧，不如买家电。万家乐&海信&华帝皇牌三免一 。活动地址：新潮门洞直走300米海信专卖店\n" +
                 "电话：0459-5354649";
        // String remove = StringUtils.remove(s, "\n");
         System.out.println(s);
     }
}
