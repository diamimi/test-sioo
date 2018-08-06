package com;

import com.pojo.SendingVo;
import com.service.SmsUserSendingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:00 2018/8/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsUserSendgdingTest {

    @Autowired
    private SmsUserSendingService smsUserSendingService;

    @Test
    public void update() {
        SendingVo vo = new SendingVo();
        vo.setUid(90418);
        List<SendingVo> voList = smsUserSendingService.findSmsUserSending(vo);
        voList.stream().parallel().forEach(v->{
            String content=v.getContent();
            if(content.contains("【爱生活】")){
                content= StringUtils.replace(content,"【爱生活】","【商虎】");
                v.setContent(content);
                v.setExpid("11"+v.getUid());
                smsUserSendingService.updateSmsUserSending(v);
            }
        });
    }

    @Test
    public void insert(){
        SendingVo vo=new SendingVo();
        vo.setUid(40067);
        vo.setRptcode("ID:1241");
        vo.setStarttime(20180802000000l);
        vo.setEndtime(20180803000000l);
        List<SendingVo> sendingVoList=smsUserSendingService.findList(vo);
        sendingVoList.parallelStream().forEach((v->{
            smsUserSendingService.insertSmsUserSending(v);
        }));
    }
}
