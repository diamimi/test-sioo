package com;

import com.pojo.MoClass;
import com.pojo.SendingVo;
import com.service.SmsUserReceiveService;
import com.util.RabbitMQProducerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 16:49 2018/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoTest {


    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;


    @Autowired
    private SmsUserReceiveService smsUserReceiveService;

    @Test
    public void sss() throws Exception {
        SendingVo vo = new SendingVo();
        vo.setStarttime(20180917000000l);
        vo.setUid(400016);
        List<SendingVo> list = smsUserReceiveService.findList(vo);
        for (SendingVo sendingVo : list) {
            String receivetime = sendingVo.getSenddate1();
            String mobile = sendingVo.getMobile() + "";
            int channel = sendingVo.getChannel();
            String content = sendingVo.getContent();
            MoClass mo = new MoClass(receivetime, channel, mobile, content, "", "0", 0);
            mo.setUid(50872);
            mo.setExpid(sendingVo.getExpid());
            rabbitMQProducerUtil.send("MO_PUSH", mo);
        }


    }
}
