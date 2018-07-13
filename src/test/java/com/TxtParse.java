package com;

import com.util.RabbitMQProducerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: HeQi
 * @Date:Create in 17:13 2018/7/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TxtParse {

    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;


   /* @Test
    public void moHandle() throws Exception {

        File file = new File("d:/mo712.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            line = StringUtils.remove(line, "[Deliver]");
            String[] split = line.split(";");
            String expidContent=split[1];
            String expid=StringUtils.substringAfter(expidContent,"dstId:");
            String phoneContent = split[3];
            String phone = StringUtils.substringAfter(phoneContent, "srcTermId:86");
            String moContent = split[4];
            String msg = StringUtils.substringAfter(moContent, "msgContent_:");


          //  System.out.println(expid+","+phone+","+msg);
            RabbitMQProducerUtil util = RabbitMQProducerUtil.getProducerInstance();
            String receivetime = "2018-07-12 14:48:27";
            int channel = 14;
            MoClass mo = new MoClass(receivetime, channel, phone, msg, expid, "0", 0);
            util.send("GATEWAY_QUEUE_MO", mo);

        }


    }*/

    @Test
    public void sa(){
        System.out.println(rabbitMQProducerUtil);
    }
}
