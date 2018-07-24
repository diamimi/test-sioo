package com;

import com.pojo.HistoryContentFor5;
import com.pojo.SendingVo;
import com.pojo.UserDayCount;
import com.service.HistoryContentFor5Service;
import com.service.SendHistoryService;
import com.service.UserDayCountService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 10:48 2018/7/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test21 {
    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private UserDayCountService userDayCountService;

    @Test
    public void sss(){
        List<UserDayCount> userDayCount = userDayCountService.getUserDayCount();
        for (UserDayCount dayCount : userDayCount) {
            System.out.println(dayCount.getUid());
        }

    }

    @Autowired
    private HistoryContentFor5Service historyContentFor5Service;

    @Test
    public void sla() {
        SendingVo sendingVo = new SendingVo();
        sendingVo.setUid(90246);
        sendingVo.setStarttime(20180720000000L);
        sendingVo.setEndtime(20180723000000L);
        List<SendingVo> list = sendHistoryService.findByConditon(sendingVo);
        for (SendingVo vo : list) {
            SendingVo v = new SendingVo();
            String content = vo.getContent();
            content = StringUtils.replace(content, "【淘达人】", "【秒白条】");
            v.setContent(content);
            v.setId(vo.getId());
            v.setStarttime(20180720000000L);
            v.setEndtime(20180723000000L);
            sendHistoryService.updateByCondition(v);
        }
    }

    @Test
    public void sla1() {
        HistoryContentFor5 sendingVo = new HistoryContentFor5();
        sendingVo.setUid(90246);
        sendingVo.setStarttime(20180720);
        sendingVo.setEndtime(20180723);
        List<HistoryContentFor5> list = historyContentFor5Service.findByConditon(sendingVo);
        for (HistoryContentFor5 vo : list) {
            HistoryContentFor5 v = new HistoryContentFor5();
            String content = vo.getContent();
            content = StringUtils.replace(content, "【淘达人】", "【秒白条】");
            v.setContent(content);
            v.setPid(vo.getPid());
            v.setStarttime(20180720);
            v.setEndtime(20180723);
            historyContentFor5Service.updateByCondition(v);
        }
    }
}
