package com.main;

import com.mongodb.BasicDBObject;
import com.pojo.SendingVo;
import com.service.AnjxService;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import com.sioo.client.cmpp.vo.DeliverVo;
import com.util.*;
import org.apache.commons.lang.StringUtils;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        LOGGER.info("=========start=========");
        ss111s();
        LOGGER.info("=========end========");
    }



    public void ss111s() {
        BSONObject where = new BasicBSONObject();
        BSONObject dayu = new BasicBSONObject("senddate", new BasicDBObject("$gt", 20181024000000L));
        BSONObject xiaoyu = new BasicBSONObject("senddate", new BasicDBObject("$lt", 20181025000000L));
        where.putAll( dayu);
        where.putAll( xiaoyu);
        where.put("uid",51134);
        List<BSONObject> list = SequoiaDBUtil.getInstance().find("sms_send_history_detail", where);
        for (BSONObject bsonObject : list) {
            LOGGER.info(bsonObject.toString());
        }
    }
    public void oow(){
        RedisUtil rUtil = RedisUtil.getInstance();
        List<String> read = FileRead.getInstance().read("/home/sioowork/114/PlatformBlockNumber.txt", "utf-8");
        read.stream().forEach(mobile->{
            if(mobile!=null&&mobile.length()>9){
                int type=MyUtils.checkMobileType(mobile);
                if(type>0){
                    int num= sendHistoryService.checkBlackMobileExsit(mobile);
                    if(num==0){
                        SendingVo vo=new SendingVo();
                        vo.setMobile(Long.parseLong(mobile));
                        vo.setId(35);
                        vo.setContent("昱桦黑名单");
                        vo.setMtype(type);
                        vo.setLevel(6);
                        try {
                            sendHistoryService.inserBlackMobile(vo);
                            rUtil.setObject("35_"+mobile, "");
                        } catch (Exception e) {

                        }
                    }

                }
            }
        });
    }

    public void lls() throws Exception {
        List<String> read = FileRead.getInstance().read("/home/sioowork/114/925.txt", "utf-8");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() ->
                read.stream().parallel().forEach(s -> {
                    String[] split = s.split("\t");
                    String mobile = split[1];
                    String id = split[0];
                    SendingVo vo = new SendingVo();
                    vo.setUid(50872);
                    vo.setMobile(Long.valueOf(mobile));
                    vo.setStarttime(20180926000000L);
                    vo.setDay(20180926);
                    vo.setEndtime(20180927000000L);
                    vo.setId(Long.parseLong(id));
                    SendingVo v = sendHistoryService.getRptBacks(vo);
                    if (v != null) {
                        if (v.getRptcode().equals("DELIVRD")) {
                            sendHistoryService.updateToSuccess(vo);
                        } else {
                            sendHistoryService.updateToFail(vo);
                        }
                    }
                })
        ).get();

    }

    public void sss() {
        LOGGER.info("=========start========");
        List<String> ids = sendHistoryService.getIds();
        FilePrintUtil.getInstance().write("/home/sioowork/114/ids.txt", ids, "utf-8");
        LOGGER.info("=========end========");
    }


    public void file() throws Exception {
        List<String> contents = FileRead.getInstance().read("/home/sioowork/114/166814_20180921-2.txt", "UTF-8");
        List<String> outs = new ArrayList<>();
        String title = "号码,pid,状态,回执时间";
        outs.add(title);
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() ->
                contents.stream().parallel().forEach(content -> {
                            String mobile = StringUtils.substringBefore(content, ",");
                            String pid = StringUtils.substringBetween(content, ",", "_");
                            SendingVo vo = new SendingVo();
                            vo.setUid(50552);
                            vo.setDay(20180921);
                            vo.setMobile(Long.valueOf(mobile));
                            vo.setPid(Integer.valueOf(pid));
                            List<DeliverVo> list = sendHistoryService.findRptPush(vo);
                            if (list != null && list.size() > 0) {
                                DeliverVo deliverVo = list.get(0);
                                String out = mobile + "," + pid + "," + deliverVo.getRpt_code() + "," + deliverVo.getRpt_time();
                                outs.add(out);
                            }
                        }
                )).get();
        FilePrintUtil.getInstance().write("/home/sioowork/114/50552.csv", outs, "GBK");
    }

    /**
     * 根据日志更新前台记录
     */
    public void updateHistoryByLog() throws Exception{
        List<String> contents = FileRead.getInstance().read("/home/data/log/deliverHandle/0925.txt", "utf-8");
        Map<String,SendingVo> map=new HashMap<>();
        for (String content : contents) {
            content=StringUtils.substringAfter(content,"dir->");
            String[] split = content.split(",");
            String rptcode=split[5];
            String id=split[2];
            String mobile=split[3];
            String rpttime=split[6];
            rpttime=rpttime.substring(0,4)+rpttime.substring(5,7)+rpttime.substring(8,10)+rpttime.substring(11,13)
                    +rpttime.substring(14,16)+rpttime.substring(17,19);
            SendingVo vo=new SendingVo();
            vo.setRpttime(Long.valueOf(rpttime));
            vo.setRptcode(rptcode);
            vo.setMobile(Long.parseLong(mobile));
            vo.setId(Long.parseLong(id));
            map.put(id,vo);
        }
        LOGGER.info("==============MAP加载完毕================");
        List<String> resolves = FileRead.getInstance().read("/home/sioowork/resolve/925.txt", "utf-8");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() ->
                resolves.stream().parallel().forEach(s -> {
                    String[] split = s.split("\t");
                    String mobile = split[1];
                    String id = split[0];
                    SendingVo vo = new SendingVo();
                    vo.setUid(50872);
                    vo.setMobile(Long.valueOf(mobile));
                    vo.setStarttime(20180925000000L);
                    vo.setEndtime(20180926000000L);
                    vo.setId(Long.parseLong(id));
                    SendingVo v = sendHistoryService.getRptBacks(vo);
                    if (v != null) {
                        if (v.getRptcode().equals("DELIVRD")) {
                            sendHistoryService.updateToSuccess(vo);
                        } else {
                            sendHistoryService.updateToFail(vo);
                        }
                    }
                })
        ).get();
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
     *
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
        String baseName = "/home/data/log/cmpp/";
        String[] files = {baseName + "50660_2.txt"};
        RedisUtil redisUtil = RedisUtil.getInstance();
        for (String file : files) {
            List<String> contents = FileRead.getInstance().read(file, "utf-8");
            int i = 0;
            for (String content : contents) {
                i++;
                if (i % 1000 == 0) {//1秒只处理1000,防止发送过快,拥堵其它用户
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
                deliver.setChannelDays(181016);
                deliver.setUserDays(181016);
                deliver.setUid(50660);
                deliver.setPid(Long.valueOf(key));
                deliver.setHisId(Long.valueOf(key));
                deliver.setChannel(1);
                rabbitMQProducerUtil.send("DELIVER_PUSH", deliver);
               // System.out.println(JSONObject.toJSONString(deliver));

            }
        }

    }

    public static void main(String[] args) {
        String content="10-16 09:03:36.740 RptPushChildThread:94 - Send Deliver Report:msgid:20181016#210842245;uid:50660;mobile:13578841299;rptCode:XA:2138;rptTime:2018-10-16 09:03:34;pid:636084477;hisid:0";
        content = StringUtils.substringAfter(content, "Send Deliver Report:");
        String[] split = content.split(";");
        String msgid = StringUtils.substringAfter(split[0], "msgid:");
        String[] split1 = msgid.split("#");
        String key = split1[1];
        String uid = StringUtils.substringAfter(split[1], "uid:");
        String mobile = StringUtils.substringAfter(split[2], "mobile:");
        String rptcode = StringUtils.substringAfter(split[3], "rptCode:");
        String rpttime = StringUtils.substringAfter(split[4], "rptTime:");
    }

}
