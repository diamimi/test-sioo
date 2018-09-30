package com.main;

import com.pojo.SendingVo;
import com.service.AnjxService;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import com.sioo.client.cmpp.vo.DeliverVo;
import com.util.FilePrintUtil;
import com.util.FileRead;
import com.util.RabbitMQProducerUtil;
import com.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);

    @Autowired
    private AnjxService anjxService;

    @Autowired
    private SendHistoryService sendHistoryService;


    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
      /*  LOGGER.info("========================start==================");
        file();
        LOGGER.info("=============end==================");*/

    }



    public void file() throws Exception{
        List<String> contents=FileRead.getInstance().read("/home/sioowork/114/166814_20180921-2.txt", "UTF-8");
        List<String> outs=new ArrayList<>();
        String title="号码,pid,状态,回执时间";
        outs.add(title);
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> contents.stream().parallel().forEach(content -> {
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
             )).get();
        FilePrintUtil.getInstance().write("/home/sioowork/114/50552.csv",outs,"GBK");
    }

    /**
     * 根据后台记录更新前台记录
     */
    public void updateHistory() throws Exception {
        List<String> contents = FileRead.getInstance().read("/home/sioowork/114/2.txt", "utf-8");
        ForkJoinPool myPool = new ForkJoinPool(8);

        myPool.submit(() -> contents.stream().parallel().forEach(i -> {
                    String[] split = i.split("\t");
                    String mobile = split[0];
                    String pid = split[1];
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.valueOf(mobile));
                    vo.setPid(Integer.parseInt(pid));
                    vo.setDay(20180925);
                    List<SendingVo> rptPush = sendHistoryService.findHistoryAndRptcode(vo);
                    if (rptPush != null && rptPush.size() > 0) {
                        String rptcode = rptPush.get(0).getRptcode();
                        vo.setRptcode(rptcode);
                        vo.setRpttime(rptPush.get(0).getRpttime());
                        vo.setTableName("0925");
                        sendHistoryService114.updateRpt(vo);
                    }
                }
        )).get();

    }

    /**
     * http推送状态
     * @throws Exception
     */
    public void httpPush() throws Exception {
        SendingVo vo = new SendingVo();
        vo.setUid(50552);
        vo.setDay(20180921);
        int i = 0;
        vo.setMobile(13853134447L);
        List<DeliverVo> list = sendHistoryService.findRptPush(vo);
        for (DeliverVo deliverVo : list) {
            i++;
            if (i % 2000 == 0) {//1秒只处理1000,防止发送过快,拥堵其它用户
                Thread.sleep(1000);
                LOGGER.info("========休息一会===============," + i);
            }
            rabbitMQProducerUtil.send("DELIVER_PUSH", deliverVo);
        }
    }

    public void lss() throws Exception {
        String baseName = "/home/sioowork/cmppservice_hy/logs/";
        String[] files = {baseName + "51133_0922.txt"};
        for (String file : files) {
            List<String> contents = FileRead.getInstance().read(file, "utf-8");
            int i = 0;
            RedisUtil redisUtil = RedisUtil.getInstance();
            for (String content : contents) {
                i++;
                if (i % 2000 == 0) {//1秒只处理1000,防止发送过快,拥堵其它用户
                    Thread.sleep(1000);
                    LOGGER.info("========休息一会===============," + i);
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

    }

}
