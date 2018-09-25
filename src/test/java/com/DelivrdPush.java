package com;

import com.alibaba.fastjson.JSONObject;
import com.sioo.client.cmpp.vo.DeliverVo;
import com.util.RabbitMQProducerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @Author: HeQi
 * @Date:Create in 9:21 2018/9/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DelivrdPush {


    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;


    @Test
    private void pushRpt(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/temp/log/90246_0617_ok.txt"),"utf-8"));
            String data = null;
            while((data = br.readLine()) != null){
                String[] s = data.split(",");
                DeliverVo vo = new DeliverVo(30, 0L, s[1], s[2], s[3]);
                vo.setChannelDays(180617);
                vo.setUserDays(180617);
                vo.setUid(90246);
                vo.setPid(Long.parseLong(s[4]));
                vo.setHisId(Long.parseLong(s[0]));
                rabbitMQProducerUtil.send("DELIVER_PUSH", vo);
                System.out.println(JSONObject.toJSONString(vo));
            }
            br.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
