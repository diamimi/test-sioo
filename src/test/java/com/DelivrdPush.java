package com;

import com.alibaba.fastjson.JSONObject;
import com.pojo.SendingVo;
import com.service.SendHistoryService;
import com.sioo.client.cmpp.vo.DeliverVo;
import com.util.FilePrintUtil;
import com.util.FileRead;
import com.util.RabbitMQProducerUtil;
import com.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 9:21 2018/9/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DelivrdPush {


    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;

    @Autowired
    private SendHistoryService sendHistoryService;


    /**
     * http推送
     *
     * @throws Exception
     */
    @Test
    public void sss() throws Exception {
        SendingVo vo = new SendingVo();
        vo.setUid(90404);
        vo.setDay(20180927);
       // vo.setMobile(13853134447L);
        List<DeliverVo> list = sendHistoryService.findRptPush(vo);
        for (DeliverVo deliverVo : list) {
            rabbitMQProducerUtil.send("DELIVER_PUSH", deliverVo);
            System.out.println(JSONObject.toJSONString(deliverVo));
        }
    }

    @Test
    public void lss() throws Exception {
        List<String> contents = FileRead.getInstance().read("D:\\hq\\files/51133_0923.txt", "utf-8");
        int i = 0;
        RedisUtil redisUtil = RedisUtil.getInstance();
        for (String content : contents) {
            i++;
            if(i%2000==0){//1秒只处理1000,防止发送过快,拥堵其它用户
                Thread.sleep(1000);
                System.out.println("休息一会");
            }
            content = StringUtils.substringAfter(content, "Send Deliver Report:");
            String[] split = content.split(";");
            String msgid = StringUtils.substringAfter(split[0], "msgid:");
            String[] split1 = msgid.split("#");
            String key = split1[1];
            String uid = StringUtils.substringAfter(split[1], "uid:");
            String mobile = StringUtils.substringAfter(split[2], "mobile:");
            String rptcode = StringUtils.substringAfter(split[3], "rptCode:");
            String rpttime = StringUtils.substringAfter(split[4], "rptTime:");
            redisUtil.putPidToMsgid(uid + "_" + key, msgid);
            DeliverVo deliver = new DeliverVo(1, 0L, mobile, rptcode, rpttime);
            deliver.setChannelDays(180921);
            deliver.setUserDays(180921);
            deliver.setUid(51133);
            deliver.setPid(Long.valueOf(key));
            deliver.setHisId(Long.valueOf(key));
            deliver.setChannel(1);
            rabbitMQProducerUtil.send("DELIVER_PUSH", deliver);

        }
    }

    /**
     * 50552导出记录
     */
    @Test
    public void file(){
        List<String> contents=FileRead.getInstance().read("D:\\hq\\files/166814_20180921-2.txt", "UTF-8");
        List<String> outs=new ArrayList<>();
        String title="号码,pid,状态,回执时间";
        outs.add(title);
        for (String content : contents) {
            String mobile=StringUtils.substringBefore(content,",");
            String pid=StringUtils.substringBetween(content,",","_");
            SendingVo vo = new SendingVo();
            vo.setUid(50552);
            vo.setDay(20180921);
            vo.setMobile(Long.valueOf(mobile));
            vo.setPid(Integer.valueOf(pid));
            List<DeliverVo> list = sendHistoryService.findRptPush(vo);
            if(list!=null&&list.size()>0){
                DeliverVo deliverVo=list.get(0);
                String out=mobile+","+pid+","+deliverVo.getRpt_code()+","+deliverVo.getRpt_time();
                outs.add(out);
            }

        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/50552.csv",outs,"GBK");
    }
}
